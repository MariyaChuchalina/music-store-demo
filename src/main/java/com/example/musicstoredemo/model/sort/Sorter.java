package com.example.musicstoredemo.model.sort;

import com.example.musicstoredemo.model.catalog.items.Accessory;
import com.example.musicstoredemo.model.catalog.items.Guitar;

import java.util.List;

public interface Sorter {

    List<Guitar> sortGuitars(List<Guitar> list);

    List<Accessory> sortAccessories(List<Accessory> list);

}
