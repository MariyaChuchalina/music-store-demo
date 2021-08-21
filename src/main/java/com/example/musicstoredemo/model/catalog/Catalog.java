package com.example.musicstoredemo.model.catalog;

import com.example.musicstoredemo.model.catalog.items.Accessory;
import com.example.musicstoredemo.model.catalog.items.Guitar;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class Catalog {

    private static Catalog catalog;

    @Getter
    @Setter
    private List<Guitar> guitarList;
    @Getter
    @Setter
    private List<Accessory> accessoryList;

    private Catalog() {

    }

    public static Catalog getInstance() {
        if (catalog == null) {
            catalog = new Catalog();
        }

        return catalog;
    }

    public CatalogMemento save() {
        return new CatalogMemento(guitarList, accessoryList);
    }

    public void revert(CatalogMemento catalog) {
        this.guitarList = catalog.getGuitarList();
        this.accessoryList = catalog.getAccessoryList();
    }

}
