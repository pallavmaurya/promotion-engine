package com.example.service.implementation;

import com.example.model.ShoppingCart;
import com.example.model.ShoppingCartItem;
import com.example.model.StockKeepingUnit;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class CheckoutServiceImplTest {

    @InjectMocks
    private CheckoutServiceImpl checkoutService;

    private ShoppingCart shoppingCart;

    private List<StockKeepingUnit> stockKeepingUnitList;

    @Before
    public void setUp() {
        stockKeepingUnitList = new ArrayList<>();
    }

    @Test
    public void checkout() {

        StockKeepingUnit stockKeepingUnitA = StockKeepingUnit.builder().skuId('A').quantity(1).build();
        StockKeepingUnit stockKeepingUnitB = StockKeepingUnit.builder().skuId('B').quantity(1).build();
        StockKeepingUnit stockKeepingUnitC = StockKeepingUnit.builder().skuId('C').quantity(1).build();
        stockKeepingUnitList.add(stockKeepingUnitA);
        stockKeepingUnitList.add(stockKeepingUnitB);
        stockKeepingUnitList.add(stockKeepingUnitC);

        ShoppingCartItem shoppingCartItemA = ShoppingCartItem.builder()
                .stockKeepingUnit(stockKeepingUnitA)
                .cartItemPrice(BigDecimal.valueOf(50))
                .promotionName(null)
                .build();
        ShoppingCartItem shoppingCartItemB = ShoppingCartItem.builder()
                .stockKeepingUnit(stockKeepingUnitB)
                .cartItemPrice(BigDecimal.valueOf(30))
                .promotionName(null)
                .build();
        ShoppingCartItem shoppingCartItemC = ShoppingCartItem.builder()
                .stockKeepingUnit(stockKeepingUnitC)
                .cartItemPrice(BigDecimal.valueOf(20))
                .promotionName(null)
                .build();

        List<ShoppingCartItem> shoppingCartItems = new ArrayList<>();
        shoppingCartItems.add(shoppingCartItemA);
        shoppingCartItems.add(shoppingCartItemB);
        shoppingCartItems.add(shoppingCartItemC);

        shoppingCart = ShoppingCart.builder()
                .shoppingCartItems(shoppingCartItems)
                .discount(BigDecimal.ZERO)
                .priceBeforeDiscount(BigDecimal.valueOf(100))
                .totalPrice(BigDecimal.valueOf(100))
                .build();

        assertEquals(shoppingCart,checkoutService.checkout(stockKeepingUnitList));
    }
}