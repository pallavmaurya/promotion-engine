package com.example.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Builder
@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
public class ShoppingCartItem {

    private final StockKeepingUnit stockKeepingUnit;

    private final BigDecimal cartItemPrice;

    private final String promotionName;

}
