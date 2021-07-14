package com.example.model;

public enum DiscountEnum {
    FIXED_PRICE("FIXED_PRICE"),
    PERCENTAGE("PERCENTAGE"),
    DEDUCTION("DEDUCTION");

    private final String value;

    DiscountEnum(String value) {
        this.value = value;
    }

    public static DiscountEnum fromValue(String value) {
        for (DiscountEnum b : values()) {
            if (b.value.equals(value)) {
                return b;
            }
        }
        throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }

    public String getValue() {
        return this.value;
    }

    @Override
    public String toString() {
        return String.valueOf(this.value);
    }
}
