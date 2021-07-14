package com.example.data;

import lombok.Builder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;


@Builder
@Entity
public class SkuPrice {

    @Id
    @Column
    private Character skuId;

    @Column
    private BigDecimal unitPrice;

    public SkuPrice(final Character skuId, final BigDecimal unitPrice) {
        this.skuId = skuId;
        this.unitPrice = unitPrice;
    }

    public SkuPrice() {
    }
}