package com.example.validation;

import com.example.model.StockKeepingUnit;
import com.example.model.StockKeepingUnits;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * This class implements UniqueSkuConstraint ConstraintValidator
 */
public class UniqueSkuConstraintValidator implements ConstraintValidator<UniqueSkuConstraint, StockKeepingUnits> {

    /**
     * @param stockKeepingUnits : Input {@link StockKeepingUnits}
     * @param context: the ConstraintValidatorContext.
     * @return True if {@link StockKeepingUnit#}.skuId are unique in {@link StockKeepingUnits}
     */
    @Override
    public boolean isValid(StockKeepingUnits stockKeepingUnits, ConstraintValidatorContext context) {
        Set<Character> skuSet = stockKeepingUnits.getStockKeepingUnits().stream().map(StockKeepingUnit::getSkuId)
                .collect(Collectors.toSet());
        return (skuSet.size() == stockKeepingUnits.getStockKeepingUnits().size());
    }
}
