package com.example.service.implementation;

import com.example.model.ShoppingCart;
import com.example.model.StockKeepingUnit;
import com.example.service.CheckoutService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CheckoutServiceImpl implements CheckoutService {
    /**
     * @param stockKeepingUnits : List of {@link StockKeepingUnit} to be added in the shopping cart
     * @return {@link ShoppingCart}: Shopping cart with list of ShoppingCartItems, discount and totalPrice
     */
    @Override
    public ShoppingCart checkout(List<StockKeepingUnit> stockKeepingUnits) {
        return null;
    }
}
