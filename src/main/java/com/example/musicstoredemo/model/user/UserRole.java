package com.example.musicstoredemo.model.user;

import java.util.Arrays;

public enum UserRole {

    ADMIN,
    CLIENT,
    UNKNOWN;

    public static UserRole parse(String type) {
        return Arrays.stream(UserRole.values())
                     .filter(t -> t.name().equalsIgnoreCase(type))
                     .findFirst()
                     .orElse(null);
    }

}
