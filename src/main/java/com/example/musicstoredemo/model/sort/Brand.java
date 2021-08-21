package com.example.musicstoredemo.model.sort;

import java.util.Arrays;

public enum Brand {

    GIBSON,
    FENDER,
    TAYLOR,
    SCHECTER,
    DUNLOP,
    MARTIN;

    public static Brand parse(String type) {
        return Arrays.stream(Brand.values())
                     .filter(t -> t.name().equalsIgnoreCase(type))
                     .findFirst()
                     .orElse(null);
    }

}
