package com.example.service.implementation;

import com.example.TestDataSetup;
import com.example.data.Promotion;
import com.example.model.DiscountEnum;
import com.example.repository.PromotionRepository;
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
public class PromotionServiceImplTest {

    @Mock
    private PromotionRepository promotionRepository;

    @InjectMocks
    private PromotionServiceImpl promotionService;

    List<Promotion> promotions;

    @Before
    public void setUp() {
        promotions = TestDataSetup.getActivePromotions();
        when(promotionRepository.findAllByActiveIsTrue()).thenReturn(promotions);

    }

    @Test
    public void getActivePromotions() {
        assertEquals(promotions, promotionService.getActivePromotions());
    }
}