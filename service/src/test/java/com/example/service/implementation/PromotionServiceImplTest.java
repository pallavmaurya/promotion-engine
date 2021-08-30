package com.example.service.implementation;

import com.example.TestDataSetup;
import com.example.data.Promotion;
import com.example.repository.PromotionRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PromotionServiceImplTest {

    List<Promotion> promotions;
    @Mock
    private PromotionRepository promotionRepository;
    @InjectMocks
    private PromotionServiceImpl promotionService;

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