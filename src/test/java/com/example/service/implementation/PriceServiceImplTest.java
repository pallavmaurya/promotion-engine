package com.example.service.implementation;

import com.example.data.SkuPrice;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class PriceServiceImplTest {

    @InjectMocks
    private PriceServiceImpl priceService;

    private SkuPrice skuPrice;

    @Before
    public void setUp(){
        skuPrice = new SkuPrice();
        skuPrice.setSkuId('A');
        skuPrice.setUnitPrice(BigDecimal.TEN);
    }

    @Test
    public void getSkuPrice() {
        assertEquals(skuPrice, priceService.getSkuPrice('A'));
    }
}