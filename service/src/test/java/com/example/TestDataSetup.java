package com.example;

import com.example.data.Promotion;
import org.openapitools.model.*;

import java.util.ArrayList;
import java.util.List;

public class TestDataSetup {

    public static List<Promotion> getActivePromotions() {

        /*
       ('A', '3 of As for 130', 1, 3, 'FIXED_PRICE', 130, TRUE),
       ('B', '2 of Bs for 45', 1, 2, 'FIXED_PRICE', 45, TRUE),
       ('CD', 'C & D for 30', 2, 1, 'FIXED_PRICE', 30, TRUE);
         */
        List<Promotion> promotions = new ArrayList<>();
        Promotion threeAPromotion = Promotion.builder()
                .skuTypes("A")
                .name("3 of As for 130")
                .priority(1)
                .lotSize(3)
                .discountType(DiscountEnum.FIXED_PRICE)
                .value(130D)
                .active(true)
                .build();
        promotions.add(threeAPromotion);
        Promotion twoBPromotion = Promotion.builder()
                .skuTypes("B")
                .name("2 of Bs for 45")
                .priority(1)
                .lotSize(2)
                .discountType(DiscountEnum.FIXED_PRICE)
                .value(45D)
                .active(true)
                .build();
        promotions.add(twoBPromotion);
        Promotion oneCDPromotion = Promotion.builder()
                .skuTypes("CD")
                .name("C & D for 30")
                .priority(2)
                .lotSize(1)
                .discountType(DiscountEnum.FIXED_PRICE)
                .value(30D)
                .active(true)
                .build();
        promotions.add(oneCDPromotion);
        return promotions;
    }

    public static StockKeepingUnits getStockKeepingUnits() {
        List<StockKeepingUnit> stockKeepingUnitList = getStockKeepingUnitsList();
        return StockKeepingUnits.builder()
                .stockKeepingUnits(stockKeepingUnitList)
                .build();
    }

    public static List<StockKeepingUnit> getStockKeepingUnitsList() {
        List<StockKeepingUnit> stockKeepingUnitList;
        stockKeepingUnitList = new ArrayList<>();
        stockKeepingUnitList.add(getStockKeepingUnit());
        return stockKeepingUnitList;
    }

    public static StockKeepingUnit getStockKeepingUnit() {
        StockKeepingUnit stockKeepingUnit;
        stockKeepingUnit = StockKeepingUnit.builder()
                .skuId("A")
                .quantity(1)
                .build();
        return stockKeepingUnit;
    }

    public static List<ShoppingCartItem> getShoppingCartItems() {
        ShoppingCartItem shoppingCartItem = getShoppingCartItem();
        List<ShoppingCartItem> shoppingCartItems;
        shoppingCartItems = new ArrayList<>();
        shoppingCartItems.add(shoppingCartItem);
        return shoppingCartItems;
    }

    public static ShoppingCartItem getShoppingCartItem() {
        StockKeepingUnit stockKeepingUnit = getStockKeepingUnit();
        ShoppingCartItem shoppingCartItem;
        shoppingCartItem = ShoppingCartItem.builder()
                .stockKeepingUnit(stockKeepingUnit)
                .cartItemPrice(10D)
                .promotionName(null)
                .build();
        return shoppingCartItem;
    }

    public static ShoppingCart getShoppingCart() {
        List<ShoppingCartItem> shoppingCartItems = getShoppingCartItems();
        ShoppingCart shoppingCart;
        shoppingCart = ShoppingCart.builder()
                .shoppingCartItems(shoppingCartItems)
                .discount(0D)
                .totalPrice(10D)
                .priceBeforeDiscount(10D)
                .build();
        return shoppingCart;
    }
}
