package com.example.service.implementation;

import com.example.data.Promotion;
import com.example.data.SkuPrice;
import com.example.exception.SkuPriceNotFoundException;
import com.example.model.ShoppingCart;
import com.example.model.ShoppingCartItem;
import com.example.model.StockKeepingUnit;
import com.example.service.CheckoutService;
import com.example.service.PriceService;
import com.example.service.PromotionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
 * This class implements the CheckoutService methods
 *
 * @author  pallavmaurya@gmail.com
 */
@Service
@RequiredArgsConstructor
public class CheckoutServiceImpl implements CheckoutService {


    private final PriceService priceService;
    private final PromotionService promotionService;

    /**
     * @param stockKeepingUnits : List of {@link StockKeepingUnit} to be added in the shopping cart
     * @return {@link ShoppingCart}: Shopping cart with list of ShoppingCartItems, discount and totalPrice
     */
    @Override
    public ShoppingCart checkout(List<StockKeepingUnit> stockKeepingUnits) {

        List<Character> skuIdsWithPromotion = new ArrayList<>();
        BigDecimal totalPrice;
        BigDecimal priceBeforeDiscount;
        List<ShoppingCartItem> cartItems;
        AtomicReference<BigDecimal> cartItemPrice = new AtomicReference<>();

        cartItems = stockKeepingUnits.stream()
                .map(stockKeepingUnit -> applyPromotions(stockKeepingUnits, skuIdsWithPromotion, stockKeepingUnit))
                .flatMap(List::stream)
                .collect(Collectors.toList());

        stockKeepingUnits.stream()
                .filter(stockKeepingUnit -> !skuIdsWithPromotion.contains(stockKeepingUnit.getSkuId()))
                .forEach(stockKeepingUnit -> {
                    cartItemPrice.set(calculateSkuPrice(stockKeepingUnit));
                    cartItems.add(buildCartItem(stockKeepingUnit, cartItemPrice.get(),
                            null,stockKeepingUnit.getQuantity()));
                });

        priceBeforeDiscount = getTotalPriceBeforeDiscount(stockKeepingUnits);
        totalPrice = cartItems.stream()
                .map(ShoppingCartItem::getCartItemPrice)
                .reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
        return ShoppingCart.builder()
                .shoppingCartItems(cartItems)
                .priceBeforeDiscount(priceBeforeDiscount)
                .discount(priceBeforeDiscount.subtract(totalPrice))
                .totalPrice(totalPrice)
                .build();
    }


    /**
     * @param stockKeepingUnits: List of {@link com.example.model.StockKeepingUnits} to be added in the shopping cart.
     * @param skuIdsWithPromotion: List of {@link StockKeepingUnit}.skuId with promotion already applied.
     * @param stockKeepingUnit: {@link StockKeepingUnit} on which promotion is to be applied.
     * @return List of {@link ShoppingCartItem}
     */
    private List<ShoppingCartItem> applyPromotions(List<StockKeepingUnit> stockKeepingUnits,
                                                   List<Character> skuIdsWithPromotion,
                                                   StockKeepingUnit stockKeepingUnit) {
        List<ShoppingCartItem> cartItems;
        Comparator<Promotion> promotionComparator;
        List<Promotion> promotions ;

        cartItems = new ArrayList<>();
        promotionComparator = Comparator.comparingInt(Promotion::getPriority);

        promotions = getValidPromotions();

        promotions.stream()
                .sorted(promotionComparator)
                .forEachOrdered(promotion -> {
                    if (isPromotionApplicable(skuIdsWithPromotion, stockKeepingUnit, promotion)) {
                        skuIdsWithPromotion.add(stockKeepingUnit.getSkuId());
                        cartItems.addAll(applyPromotion(stockKeepingUnit, promotion));
                    }
                });
        return cartItems;
    }

    /**
     * @param skuIdsWithPromotion: List of {@link StockKeepingUnit}.skuId with promotion already applied.
     * @param stockKeepingUnit: {@link StockKeepingUnit} to be evaluated for applying promotion
     * @param promotion: {@link Promotion} to be checked if applicable to input stockKeepingUnit
     * @return True, If the promotion can be applied ,Else False.
     */
    private boolean isPromotionApplicable(Collection<Character> skuIdsWithPromotion,
                                                   StockKeepingUnit stockKeepingUnit, Promotion promotion) {
        Character skuId = stockKeepingUnit.getSkuId();
        if (skuIdsWithPromotion.contains(skuId))
            return false;
        else if (1 == promotion.getSkuTypes().length())
            return (0 == skuId.compareTo(Character.toUpperCase(promotion.getSkuTypes().toCharArray()[0])))
                    && (stockKeepingUnit.getQuantity() >= promotion.getLotSize());
        else
            return false;
    }

    /**
     * @param stockKeepingUnit: {@link StockKeepingUnit} on which promotion is to be applied
     * @param promotion: {@link Promotion} to be applied on StockKeeping Unit
     * @return List of {@link ShoppingCartItem}
     */
    private List<ShoppingCartItem> applyPromotion(StockKeepingUnit stockKeepingUnit, Promotion promotion) {
        List<ShoppingCartItem> cartItems;
        int promotedQuantity;
        int unPromotedQuantity;
        BigDecimal promotedCartItemPrice;
        BigDecimal unPromotedCartItemPrice;

        promotedQuantity = (stockKeepingUnit.getQuantity() / promotion.getLotSize()) * promotion.getLotSize();
        unPromotedQuantity = (stockKeepingUnit.getQuantity() % promotion.getLotSize());
        promotedCartItemPrice = BigDecimal.valueOf(stockKeepingUnit.getQuantity() / promotion.getLotSize())
                .multiply(promotion.getValue());

        cartItems = new ArrayList<>();
        cartItems.add(buildCartItem(stockKeepingUnit, promotedCartItemPrice, promotion.getName(), promotedQuantity));

        if (0 < unPromotedQuantity) {
            unPromotedCartItemPrice = BigDecimal.valueOf(unPromotedQuantity).multiply(getUnitPrice(stockKeepingUnit));
            cartItems.add(buildCartItem(stockKeepingUnit, unPromotedCartItemPrice, null, unPromotedQuantity));
        }
        return cartItems;
    }

    /**
     * @return List of Active Promotions with a non zero lotSize (to prevent divide by zero Exception)
     */
    private List<Promotion> getValidPromotions() {
        return promotionService.getActivePromotions().stream()
                .filter(promotion -> promotion.getLotSize() > 0).collect(Collectors.toList());
    }

    /**
     * @param stockKeepingUnit: {@link com.example.model.StockKeepingUnit}
     * @return total Price of the stockKeepingUnit before discount
     */
    private BigDecimal calculateSkuPrice(StockKeepingUnit stockKeepingUnit) {
        BigDecimal unitPrice = getUnitPrice(stockKeepingUnit);
        BigDecimal quantity = BigDecimal.valueOf(stockKeepingUnit.getQuantity());
        return unitPrice.multiply(quantity);
    }

    /**
     * @param stockKeepingUnit: {@link com.example.model.StockKeepingUnit}
     * @return Unit Price of the stockKeepingUnit before discount
     */
    private BigDecimal getUnitPrice(StockKeepingUnit stockKeepingUnit) {
        Character skuId = stockKeepingUnit.getSkuId();
        SkuPrice skuPrice = priceService.getSkuPrice(skuId);
        return skuPrice.getUnitPrice();
    }


    /**
     * @param stockKeepingUnits: List of {@link com.example.model.StockKeepingUnit}
     * @return total price of all stockKeepingUnits before discount
     */
    private BigDecimal getTotalPriceBeforeDiscount(Collection<StockKeepingUnit> stockKeepingUnits) {
        return stockKeepingUnits.stream()
                .map(this::calculateSkuPrice)
                .reduce(BigDecimal::add)
                .orElseThrow(SkuPriceNotFoundException::new);
    }

    /**
     * @param stockKeepingUnit: {@link StockKeepingUnit} for this cart item.
     * @param cartItemPrice: Calculated Price to be set for this cart item.
     * @param quantity: Quantity of the stockKeepingUnit in this cart Item.
     * @return cart item {@link ShoppingCartItem}
     */
    private ShoppingCartItem buildCartItem(StockKeepingUnit stockKeepingUnit, BigDecimal cartItemPrice,
                                           String promotionName, int quantity) {
        return ShoppingCartItem.builder()
                .stockKeepingUnit(getStockKeepingUnit(stockKeepingUnit, quantity))
                .cartItemPrice(cartItemPrice)
                .promotionName(promotionName)
                .build();
    }

    /**
     * @param stockKeepingUnit
     * @param quantity
     * @return
     */
    private StockKeepingUnit getStockKeepingUnit(StockKeepingUnit stockKeepingUnit, int quantity) {
        return StockKeepingUnit.builder()
                .skuId(stockKeepingUnit.getSkuId())
                .quantity(quantity)
                .build();
    }

}
