package com.example.musicstoredemo.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CatalogMemento {

    private List<Guitar> guitarList;
    private List<Accessory> accessoryList;
}
