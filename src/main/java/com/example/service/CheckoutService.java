package com.example.service;

import com.example.model.ShoppingCart;
import com.example.model.StockKeepingUnit;

import java.util.List;

public interface CheckoutService {

    /**
     * @param stockKeepingUnits: List of {@link StockKeepingUnit} to be added in the shopping cart
     * @return {@link ShoppingCart}: Shopping cart with list of ShoppingCartItems, discount and totalPrice
     */
    ShoppingCart checkout(List<StockKeepingUnit> stockKeepingUnits);
}
