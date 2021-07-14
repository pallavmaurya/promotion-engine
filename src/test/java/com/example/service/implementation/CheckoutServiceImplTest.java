package com.example.service.implementation;

import com.example.TestDataSetup;
import com.example.data.SkuPrice;
import com.example.model.ShoppingCart;
import com.example.model.ShoppingCartItem;
import com.example.model.StockKeepingUnit;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CheckoutServiceImplTest {

    @Mock
    private PriceServiceImpl priceService;

    @Mock
    private PromotionServiceImpl promotionService;

    @InjectMocks
    private CheckoutServiceImpl checkoutService;

    private ShoppingCart shoppingCart;

    private List<StockKeepingUnit> stockKeepingUnitList;

    @Before
    public void setUp() {
        stockKeepingUnitList = new ArrayList<>();

        when(priceService.getSkuPrice('A'))
                .thenReturn(SkuPrice.builder().skuId('A').unitPrice(BigDecimal.valueOf(50)).build());
        when(priceService.getSkuPrice('B'))
                .thenReturn(SkuPrice.builder().skuId('B').unitPrice(BigDecimal.valueOf(30)).build());
        when(priceService.getSkuPrice('C'))
                .thenReturn(SkuPrice.builder().skuId('C').unitPrice(BigDecimal.valueOf(20)).build());

        when(promotionService.getActivePromotions())
                .thenReturn(TestDataSetup.getActivePromotions());
    }

    @Test
    public void checkoutScenario1() {

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

    @Test
    public void checkoutScenario2() {
        StockKeepingUnit stockKeepingUnitA = StockKeepingUnit.builder().skuId('A').quantity(5).build();
        StockKeepingUnit stockKeepingUnitB = StockKeepingUnit.builder().skuId('B').quantity(5).build();
        StockKeepingUnit stockKeepingUnitC = StockKeepingUnit.builder().skuId('C').quantity(1).build();
        stockKeepingUnitList.add(stockKeepingUnitA);
        stockKeepingUnitList.add(stockKeepingUnitB);
        stockKeepingUnitList.add(stockKeepingUnitC);

        StockKeepingUnit stockKeepingUnitAWithPromotion = StockKeepingUnit.builder().skuId('A').quantity(3).build();
        ShoppingCartItem shoppingCartItemAWithPromotion = ShoppingCartItem.builder()
                .stockKeepingUnit(stockKeepingUnitAWithPromotion)
                .cartItemPrice(BigDecimal.valueOf(130))
                .promotionName("3 of As for 130")
                .build();

        StockKeepingUnit stockKeepingUnitAWithoutPromotion = StockKeepingUnit.builder().skuId('A').quantity(2).build();
        ShoppingCartItem shoppingCartItemAWithoutPromotion = ShoppingCartItem.builder()
                .stockKeepingUnit(stockKeepingUnitAWithoutPromotion)
                .cartItemPrice(BigDecimal.valueOf(100))
                .promotionName(null)
                .build();

        StockKeepingUnit stockKeepingUnitBWithPromotion = StockKeepingUnit.builder().skuId('B').quantity(4).build();
        ShoppingCartItem shoppingCartItemBWithPromotion = ShoppingCartItem.builder()
                .stockKeepingUnit(stockKeepingUnitBWithPromotion)
                .cartItemPrice(BigDecimal.valueOf(90))
                .promotionName("2 of Bs for 45")
                .build();

        StockKeepingUnit stockKeepingUnitBWithoutPromotion = StockKeepingUnit.builder().skuId('B').quantity(1).build();
        ShoppingCartItem shoppingCartItemBWithoutPromotion = ShoppingCartItem.builder()
                .stockKeepingUnit(stockKeepingUnitBWithoutPromotion)
                .cartItemPrice(BigDecimal.valueOf(30))
                .promotionName(null)
                .build();

        ShoppingCartItem shoppingCartItemC = ShoppingCartItem.builder()
                .stockKeepingUnit(stockKeepingUnitC)
                .cartItemPrice(BigDecimal.valueOf(20))
                .promotionName(null)
                .build();

        List<ShoppingCartItem> shoppingCartItems = new ArrayList<>();
        shoppingCartItems.add(shoppingCartItemAWithPromotion);
        shoppingCartItems.add(shoppingCartItemAWithoutPromotion);
        shoppingCartItems.add(shoppingCartItemBWithPromotion);
        shoppingCartItems.add(shoppingCartItemBWithoutPromotion);
        shoppingCartItems.add(shoppingCartItemC);

        shoppingCart = ShoppingCart.builder()
                .shoppingCartItems(shoppingCartItems)
                .discount(BigDecimal.valueOf(50))
                .priceBeforeDiscount(BigDecimal.valueOf(420))
                .totalPrice(BigDecimal.valueOf(370))
                .build();

        assertEquals(shoppingCart,checkoutService.checkout(stockKeepingUnitList));
    }
}