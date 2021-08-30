package com.example.validation;

import com.example.TestDataSetup;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.openapitools.model.StockKeepingUnits;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


@RunWith(MockitoJUnitRunner.class)
public class UniqueSkuConstraintValidatorTest {


    private final UniqueSkuConstraintValidator validator = new UniqueSkuConstraintValidator();
    StockKeepingUnits stockKeepingUnits;


    @Before
    public void setUp() {
        stockKeepingUnits = TestDataSetup.getStockKeepingUnits();
    }

    @Test
    public void isValid() {
        assertTrue(validator.isValid(stockKeepingUnits, null));
    }

    @Test
    public void isInValid() {
        stockKeepingUnits.getStockKeepingUnits().addAll(TestDataSetup.getStockKeepingUnits().getStockKeepingUnits());
        assertFalse(validator.isValid(stockKeepingUnits, null));
    }

}