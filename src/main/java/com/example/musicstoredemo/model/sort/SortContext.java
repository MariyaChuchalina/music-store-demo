package com.example.musicstoredemo.model.sort;

import com.example.musicstoredemo.model.catalog.items.Accessory;
import com.example.musicstoredemo.model.catalog.items.Guitar;
import lombok.Setter;

import java.util.List;

@Setter
public class SortContext {

    private Sorter sorter;

    public void sortGuitars(List<Guitar> listToSort) {
        sorter.sortGuitars(listToSort);
    }

    public void sortAccessories(List<Accessory> listToSort) {
        sorter.sortAccessories(listToSort);
    }

}
