package com.example.musicstoredemo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum Type {
    ACOUSTIC("acoustic"),
    ELECTRIC("electric"),
    HYBRID("hybrid");

    private String name;

    public Type parse(String type) {
        return Arrays.stream(Type.values())
                .filter(t -> t.name.equals(type))
                .findFirst()
                .orElse(null);
    }
}
