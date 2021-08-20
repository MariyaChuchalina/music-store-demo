package com.example.musicstoredemo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum GuitarType {
    ACOUSTIC,
    ELECTRIC,
    HYBRID;

    public GuitarType parse(String type) {
        return Arrays.stream(GuitarType.values())
                .filter(t -> t.name().equalsIgnoreCase(type))
                .findFirst()
                .orElse(null);
    }
}
