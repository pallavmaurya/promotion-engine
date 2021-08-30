package com.example.data;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;


@Builder
@Entity
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class SkuPrice {

    @Id
    @Column
    private Character skuId;

    @Column
    private Double unitPrice;
}