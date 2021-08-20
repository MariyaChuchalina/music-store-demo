package com.example.musicstoredemo.model.catalog;

import com.example.musicstoredemo.model.Accessory;
import com.example.musicstoredemo.model.Guitar;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CatalogMemento {

    private List<Guitar> guitarList;
    private List<Accessory> accessoryList;
}
