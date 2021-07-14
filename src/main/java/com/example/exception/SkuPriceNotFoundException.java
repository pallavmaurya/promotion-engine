package com.example.exception;

public class SkuPriceNotFoundException extends RuntimeException {

    private static final String message = "Sku price entry does not exist";

    public SkuPriceNotFoundException() {
        super(message);
    }
}