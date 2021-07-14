package com.example.data;

import com.example.model.DiscountEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity
@Builder
@Getter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
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
}
