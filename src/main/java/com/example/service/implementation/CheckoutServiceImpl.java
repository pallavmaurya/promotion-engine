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

        cartItems = new ArrayList<>();

        stockKeepingUnits.stream()
                .filter(stockKeepingUnit -> !skuIdsWithPromotion.contains(stockKeepingUnit.getSkuId()))
                .forEach(stockKeepingUnit -> {
                    cartItemPrice.set(this.calculateSkuPrice(stockKeepingUnit));
                    cartItems.add(this.buildCartItem(stockKeepingUnit, cartItemPrice.get(), stockKeepingUnit.getQuantity()));
                });

        priceBeforeDiscount = this.getTotalPriceBeforeDiscount(stockKeepingUnits);
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
        SkuPrice skuPrice = this.priceService.getSkuPrice(skuId);
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
                                           int quantity) {
        return ShoppingCartItem.builder()
                .stockKeepingUnit(getStockKeepingUnit(stockKeepingUnit, quantity))
                .cartItemPrice(cartItemPrice)
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
