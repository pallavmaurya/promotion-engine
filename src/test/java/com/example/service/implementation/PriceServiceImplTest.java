package com.example.service.implementation;

import com.example.data.SkuPrice;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import com.example.repository.PriceRepository;
import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PriceServiceImplTest {

    @Mock
    private PriceRepository priceRepository;

    @InjectMocks
    private PriceServiceImpl priceService;

    private SkuPrice skuPrice;

    @Before
    public void setUp(){
        skuPrice = new SkuPrice();
        skuPrice.setSkuId('A');
        skuPrice.setUnitPrice(BigDecimal.TEN);
        when(priceRepository.findById('A')).thenReturn(Optional.of(skuPrice));

    }

    @Test
    public void getSkuPrice() {
        assertEquals(skuPrice, priceService.getSkuPrice('A'));
    }
}