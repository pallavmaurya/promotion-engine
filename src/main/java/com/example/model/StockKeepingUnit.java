package com.example.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
public class StockKeepingUnit {

    private final Character skuId;

    private final int quantity;

}
