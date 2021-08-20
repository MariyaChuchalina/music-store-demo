package com.example.musicstoredemo.model.catalog;

import java.util.Stack;

public class Caretaker {

    private Stack<CatalogMemento> catalogHistory = new Stack<>();

    public void save(Catalog catalog) {
        if (catalogHistory.empty() || !isAlredyInStack(catalog, catalogHistory.peek())) {
            catalogHistory.push(catalog.save());
        }
    }

    public void revert(Catalog catalog) {
        if (!catalogHistory.empty()) {
            catalog.revert(catalogHistory.pop());
        }
    }

    private boolean isAlredyInStack(Catalog catalog, CatalogMemento catalogMemento) {
        return catalog.getGuitarList().equals(catalogMemento.getGuitarList()) && catalog.getAccessoryList().equals(catalogMemento.getAccessoryList());
    }

}
