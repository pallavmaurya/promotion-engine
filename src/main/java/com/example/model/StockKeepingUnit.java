package com.example.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Builder
@EqualsAndHashCode
public class StockKeepingUnit {

    @JsonProperty("skuId")
    private final Character skuId;

    @JsonProperty("quantity")
    private final int quantity;

    public StockKeepingUnit(Character skuId, int quantity) {
        this.skuId = skuId;
        this.quantity = quantity;
    }

    @NotNull(message = "skuId cannot be null")
    public Character getSkuId() {
        return skuId;
    }

    @Min(value = 1, message = "Quantity should be greater than 0")
    public int getQuantity() {
        return this.quantity;
    }

}
