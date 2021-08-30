package com.example.data;

import lombok.*;
import org.openapitools.model.DiscountEnum;

import javax.persistence.*;

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
    private Double value;

    @Column
    private boolean active;
}
