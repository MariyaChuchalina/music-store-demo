package com.example.musicstoredemo.model.sort;

import com.example.musicstoredemo.model.catalog.items.Accessory;
import com.example.musicstoredemo.model.catalog.items.Guitar;

import java.util.Collections;
import java.util.List;

public class BrandSorter implements Sorter {

    @Override
    public List<Guitar> sortGuitars(List<Guitar> list) {
        Collections.sort(list, (guitar1, guitar2) -> guitar1.getBrand().compareToIgnoreCase(guitar2.getBrand()));
        return list;
    }

    @Override
    public List<Accessory> sortAccessories(List<Accessory> list) {
        Collections.sort(list, (accessory1, accessory2) -> accessory1.getBrand().compareToIgnoreCase(accessory2.getBrand()));
        return list;
    }
}
