package com.example.musicstoredemo.model.price;

import java.util.Arrays;

public enum Currency {

    EURO,
    DOLLAR,
    POUND;

    public static Currency parse(String type) {
        return Arrays.stream(Currency.values())
                     .filter(t -> t.name().equalsIgnoreCase(type))
                     .findFirst()
                     .orElse(null);
    }

}
