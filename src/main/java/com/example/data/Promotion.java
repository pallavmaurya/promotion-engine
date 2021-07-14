package com.example.data;

import com.example.model.DiscountEnum;
import lombok.Builder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity
@Builder
public class Promotion {


    @Id
    private String skuTypes;

    @Column
    private String name;

    @Column
    private int priority;

    @Column
    private int lotSize;

    @Column
    @Enumerated(EnumType.STRING)
    private DiscountEnum discountType;

    @Column
    private BigDecimal value;

    @Column
    private boolean active;

    public Promotion(final String skuTypes, final String name, final int priority, final int lotSize, final DiscountEnum discountType, final BigDecimal value, final boolean active) {
        this.skuTypes = skuTypes;
        this.name = name;
        this.priority = priority;
        this.lotSize = lotSize;
        this.discountType = discountType;
        this.value = value;
        this.active = active;
    }

    public Promotion() {

    }
}
