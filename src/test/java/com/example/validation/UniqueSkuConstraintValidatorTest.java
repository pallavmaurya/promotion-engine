package com.example.validation;

import com.example.TestDataSetup;
import com.example.model.StockKeepingUnits;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;


@RunWith(MockitoJUnitRunner.class)
public class UniqueSkuConstraintValidatorTest {


    private final UniqueSkuConstraintValidator validator = new UniqueSkuConstraintValidator();
    StockKeepingUnits stockKeepingUnits;


    @Before
    public void setUp(){
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