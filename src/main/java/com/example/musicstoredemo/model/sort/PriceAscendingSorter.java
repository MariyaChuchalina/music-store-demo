package com.example.musicstoredemo.model.sort;

import com.example.musicstoredemo.model.catalog.items.Accessory;
import com.example.musicstoredemo.model.catalog.items.Guitar;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class PriceAscendingSorter implements Sorter {

    @Override
    public List<Guitar> sortGuitars(List<Guitar> list) {
        Collections.sort(list, Comparator.comparingDouble(Guitar::getPrice));
        return list;
    }

    @Override
    public List<Accessory> sortAccessories(List<Accessory> list) {
        Collections.sort(list, Comparator.comparingDouble(Accessory::getPrice));
        return list;
    }
}
