package com.example.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Pojo for StockKeepingUnit
 *
 * @author pallavmaurya@gmail.com
 */
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StockKeepingUnits {

    private List<StockKeepingUnit> stockKeepingUnits;

}
