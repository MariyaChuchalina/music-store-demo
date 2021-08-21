package com.example.musicstoredemo.model.sort;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@AllArgsConstructor
@Getter
public enum SortStrategy {

    SORT_BY_BRAND("brand"),
    SORT_BY_TYPE("type"),
    SORT_BY_PRICE_ASCENDING("ascending"),
    SORT_BY_PRICE_DESCENDING("descending"),
    NONE("none");

    private String type;

    public static SortStrategy parse(String type) {
        return Arrays.stream(SortStrategy.values())
                     .filter(t -> t.getType().equalsIgnoreCase(type))
                     .findFirst()
                     .orElse(null);
    }

}
