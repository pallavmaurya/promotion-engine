package com.example.model;

import java.math.BigDecimal;
import java.util.List;

public class ShoppingCart {

    private final List<ShoppingCartItem> shoppingCartItems;

    private final BigDecimal priceBeforeDiscount;

    private final BigDecimal discount;

    private final BigDecimal totalPrice;

    public ShoppingCart(final List<ShoppingCartItem> shoppingCartItems, final BigDecimal priceBeforeDiscount, final BigDecimal discount, final BigDecimal totalPrice) {
        this.shoppingCartItems = shoppingCartItems;
        this.priceBeforeDiscount = priceBeforeDiscount;
        this.discount = discount;
        this.totalPrice = totalPrice;
    }
}
