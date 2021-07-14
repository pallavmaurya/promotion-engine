package com.example.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class ShoppingCartItem {

    private final StockKeepingUnit stockKeepingUnit;

    private final BigDecimal cartItemPrice;

    private final String promotionName;

}
