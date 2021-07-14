package com.example.model;

public class StockKeepingUnit {

    private final Character skuId;

    private final int quantity;

    public StockKeepingUnit(final Character skuId, final int quantity) {
        this.skuId = skuId;
        this.quantity = quantity;
    }
}
