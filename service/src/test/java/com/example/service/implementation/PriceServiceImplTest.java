package com.example.service.implementation;

import com.example.data.SkuPrice;
import com.example.repository.PriceRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

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
    public void setUp() {
        skuPrice = SkuPrice.builder()
                .skuId('A')
                .unitPrice(10D)
                .build();
        when(priceRepository.findById('A')).thenReturn(Optional.of(skuPrice));

    }

    @Test
    public void getSkuPrice() {
        assertEquals(skuPrice, priceService.getSkuPrice('A'));
    }
}