package com.example.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
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

    @Valid
    @JsonProperty("stockKeepingUnits")
    private List<StockKeepingUnit> stockKeepingUnits;

}
