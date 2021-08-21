package com.example.musicstoredemo.model.sort;

import com.example.musicstoredemo.model.catalog.items.Accessory;
import com.example.musicstoredemo.model.catalog.items.Guitar;

import java.util.Collections;
import java.util.List;

public class TypeSorter implements Sorter {

    @Override
    public List<Guitar> sortGuitars(List<Guitar> list) {
        Collections.sort(list, (guitar1, guitar2) -> guitar1.getType().compareToIgnoreCase(guitar2.getType()));
        return list;
    }

    @Override
    public List<Accessory> sortAccessories(List<Accessory> list) {
        Collections.sort(list, (accessory1, accessory2) -> accessory1.getType().compareToIgnoreCase(accessory2.getType()));
        return list;
    }
}
