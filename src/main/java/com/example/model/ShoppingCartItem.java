package com.example.model;

import java.math.BigDecimal;

public class ShoppingCartItem {

    private final StockKeepingUnit stockKeepingUnit;

    private final BigDecimal cartItemPrice;

    private final String promotionName;

    public ShoppingCartItem(final StockKeepingUnit stockKeepingUnit, final BigDecimal cartItemPrice, final String promotionName) {
        this.stockKeepingUnit = stockKeepingUnit;
        this.cartItemPrice = cartItemPrice;
        this.promotionName = promotionName;
    }
}
