package com.example.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Builder
@Getter
@Setter
@EqualsAndHashCode
public class ShoppingCart {

    private final List<ShoppingCartItem> shoppingCartItems;

    private final BigDecimal priceBeforeDiscount;

    private final BigDecimal discount;

    private final BigDecimal totalPrice;

}
