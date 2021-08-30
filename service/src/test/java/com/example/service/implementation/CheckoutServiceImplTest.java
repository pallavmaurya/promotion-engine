package com.example.service.implementation;

import com.example.TestDataSetup;
import com.example.data.SkuPrice;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.openapitools.model.ShoppingCart;
import org.openapitools.model.ShoppingCartItem;
import org.openapitools.model.StockKeepingUnit;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
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
                .thenReturn(SkuPrice.builder().skuId('A').unitPrice(50D).build());
        when(priceService.getSkuPrice('B'))
                .thenReturn(SkuPrice.builder().skuId('B').unitPrice(30D).build());
        when(priceService.getSkuPrice('C'))
                .thenReturn(SkuPrice.builder().skuId('C').unitPrice(20D).build());
        when(priceService.getSkuPrice('D'))
                .thenReturn(SkuPrice.builder().skuId('D').unitPrice(15D).build());
        when(promotionService.getActivePromotions())
                .thenReturn(TestDataSetup.getActivePromotions());
    }

    @Test
    public void checkoutScenario1() {

        StockKeepingUnit stockKeepingUnitA = StockKeepingUnit.builder().skuId("A").quantity(1).build();
        StockKeepingUnit stockKeepingUnitB = StockKeepingUnit.builder().skuId("B").quantity(1).build();
        StockKeepingUnit stockKeepingUnitC = StockKeepingUnit.builder().skuId("C").quantity(1).build();
        stockKeepingUnitList.add(stockKeepingUnitA);
        stockKeepingUnitList.add(stockKeepingUnitB);
        stockKeepingUnitList.add(stockKeepingUnitC);

        ShoppingCartItem shoppingCartItemA = ShoppingCartItem.builder()
                .stockKeepingUnit(stockKeepingUnitA)
                .cartItemPrice(50D)
                .promotionName(null)
                .build();
        ShoppingCartItem shoppingCartItemB = ShoppingCartItem.builder()
                .stockKeepingUnit(stockKeepingUnitB)
                .cartItemPrice(30D)
                .promotionName(null)
                .build();
        ShoppingCartItem shoppingCartItemC = ShoppingCartItem.builder()
                .stockKeepingUnit(stockKeepingUnitC)
                .cartItemPrice(20D)
                .promotionName(null)
                .build();

        List<ShoppingCartItem> shoppingCartItems = new ArrayList<>();
        shoppingCartItems.add(shoppingCartItemA);
        shoppingCartItems.add(shoppingCartItemB);
        shoppingCartItems.add(shoppingCartItemC);

        shoppingCart = ShoppingCart.builder()
                .shoppingCartItems(shoppingCartItems)
                .discount(0D)
                .priceBeforeDiscount(100D)
                .totalPrice(100D)
                .build();

        assertEquals(shoppingCart, checkoutService.checkout(stockKeepingUnitList));
    }

    @Test
    public void checkoutScenario2() {
        StockKeepingUnit stockKeepingUnitA = StockKeepingUnit.builder().skuId("A").quantity(5).build();
        StockKeepingUnit stockKeepingUnitB = StockKeepingUnit.builder().skuId("B").quantity(5).build();
        StockKeepingUnit stockKeepingUnitC = StockKeepingUnit.builder().skuId("C").quantity(1).build();
        stockKeepingUnitList.add(stockKeepingUnitA);
        stockKeepingUnitList.add(stockKeepingUnitB);
        stockKeepingUnitList.add(stockKeepingUnitC);

        StockKeepingUnit stockKeepingUnitAWithPromotion = StockKeepingUnit.builder().skuId("A").quantity(3).build();
        ShoppingCartItem shoppingCartItemAWithPromotion = ShoppingCartItem.builder()
                .stockKeepingUnit(stockKeepingUnitAWithPromotion)
                .cartItemPrice(130D)
                .promotionName("3 of As for 130")
                .build();

        StockKeepingUnit stockKeepingUnitAWithoutPromotion = StockKeepingUnit.builder().skuId("A").quantity(2).build();
        ShoppingCartItem shoppingCartItemAWithoutPromotion = ShoppingCartItem.builder()
                .stockKeepingUnit(stockKeepingUnitAWithoutPromotion)
                .cartItemPrice(100D)
                .promotionName(null)
                .build();

        StockKeepingUnit stockKeepingUnitBWithPromotion = StockKeepingUnit.builder().skuId("B").quantity(4).build();
        ShoppingCartItem shoppingCartItemBWithPromotion = ShoppingCartItem.builder()
                .stockKeepingUnit(stockKeepingUnitBWithPromotion)
                .cartItemPrice(90D)
                .promotionName("2 of Bs for 45")
                .build();

        StockKeepingUnit stockKeepingUnitBWithoutPromotion = StockKeepingUnit.builder().skuId("B").quantity(1).build();
        ShoppingCartItem shoppingCartItemBWithoutPromotion = ShoppingCartItem.builder()
                .stockKeepingUnit(stockKeepingUnitBWithoutPromotion)
                .cartItemPrice(30D)
                .promotionName(null)
                .build();

        ShoppingCartItem shoppingCartItemC = ShoppingCartItem.builder()
                .stockKeepingUnit(stockKeepingUnitC)
                .cartItemPrice(20D)
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
                .discount(50D)
                .priceBeforeDiscount(420D)
                .totalPrice(370D)
                .build();

        assertEquals(shoppingCart, checkoutService.checkout(stockKeepingUnitList));
    }

    @Test
    public void checkout_scenario3() {
        StockKeepingUnit stockKeepingUnitA = StockKeepingUnit.builder().skuId("A").quantity(3).build();
        StockKeepingUnit stockKeepingUnitB = StockKeepingUnit.builder().skuId("B").quantity(5).build();
        StockKeepingUnit stockKeepingUnitC = StockKeepingUnit.builder().skuId("C").quantity(1).build();
        StockKeepingUnit stockKeepingUnitD = StockKeepingUnit.builder().skuId("D").quantity(1).build();
        stockKeepingUnitList.add(stockKeepingUnitA);
        stockKeepingUnitList.add(stockKeepingUnitB);
        stockKeepingUnitList.add(stockKeepingUnitC);
        stockKeepingUnitList.add(stockKeepingUnitD);

        ShoppingCartItem shoppingCartItemA = ShoppingCartItem.builder()
                .stockKeepingUnit(stockKeepingUnitA)
                .cartItemPrice(130D)
                .promotionName("3 of As for 130")
                .build();

        StockKeepingUnit stockKeepingUnitBWithPromotion = StockKeepingUnit.builder().skuId("B").quantity(4).build();
        ShoppingCartItem shoppingCartItemBWithPromotion = ShoppingCartItem.builder()
                .stockKeepingUnit(stockKeepingUnitBWithPromotion)
                .cartItemPrice(90D)
                .promotionName("2 of Bs for 45")
                .build();

        StockKeepingUnit stockKeepingUnitBWithoutPromotion = StockKeepingUnit.builder().skuId("B").quantity(1).build();
        ShoppingCartItem shoppingCartItemBWithoutPromotion = ShoppingCartItem.builder()
                .stockKeepingUnit(stockKeepingUnitBWithoutPromotion)
                .cartItemPrice(30D)
                .promotionName(null)
                .build();

        ShoppingCartItem shoppingCartItemC = ShoppingCartItem.builder()
                .stockKeepingUnit(stockKeepingUnitC)
                .cartItemPrice(0D)
                .promotionName("C & D for 30")
                .build();

        ShoppingCartItem shoppingCartItemD = ShoppingCartItem.builder()
                .stockKeepingUnit(stockKeepingUnitD)
                .cartItemPrice(30D)
                .promotionName("C & D for 30")
                .build();

        List<ShoppingCartItem> shoppingCartItems = new ArrayList<>();
        shoppingCartItems.add(shoppingCartItemA);
        shoppingCartItems.add(shoppingCartItemBWithPromotion);
        shoppingCartItems.add(shoppingCartItemBWithoutPromotion);
        shoppingCartItems.add(shoppingCartItemC);
        shoppingCartItems.add(shoppingCartItemD);

        shoppingCart = ShoppingCart.builder()
                .shoppingCartItems(shoppingCartItems)
                .discount(55D)
                .priceBeforeDiscount(335D)
                .totalPrice(280D)
                .build();

        assertEquals(shoppingCart, checkoutService.checkout(stockKeepingUnitList));
    }
}