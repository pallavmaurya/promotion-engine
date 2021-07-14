package com.example.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class StockKeepingUnits {

    private final List<StockKeepingUnit> stockKeepingUnits;

}
