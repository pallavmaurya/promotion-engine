package com.example;

import com.example.data.Promotion;
import com.example.model.DiscountEnum;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class TestDataSetup {

    public static List<Promotion> getActivePromotions(){

        /*
       ('A', '3 of As for 130', 1, 3, 'FIXED_PRICE', 130, TRUE),
       ('B', '2 of Bs for 45', 1, 2, 'FIXED_PRICE', 45, TRUE),
       ('CD', 'C & D for 30', 2, 1, 'FIXED_PRICE', 30, TRUE);
         */
        List<Promotion> promotions = new ArrayList<>();
        Promotion threeAPromotion = Promotion.builder()
                .skuTypes("A")
                .name("3 of As for 130")
                .priority(1)
                .lotSize(3)
                .discountType(DiscountEnum.FIXED_PRICE)
                .value(BigDecimal.valueOf(130))
                .active(true)
                .build();
        promotions.add(threeAPromotion);
        Promotion twoBPromotion = Promotion.builder()
                .skuTypes("B")
                .name("2 of Bs for 45")
                .priority(1)
                .lotSize(2)
                .discountType(DiscountEnum.FIXED_PRICE)
                .value(BigDecimal.valueOf(45))
                .active(true)
                .build();
        promotions.add(twoBPromotion);
        Promotion oneCDPromotion = Promotion.builder()
                .skuTypes("CD")
                .name("C & D for 30")
                .priority(2)
                .lotSize(1)
                .discountType(DiscountEnum.FIXED_PRICE)
                .value(BigDecimal.valueOf(30))
                .active(true)
                .build();
        promotions.add(oneCDPromotion);
        return promotions;
    }
}
