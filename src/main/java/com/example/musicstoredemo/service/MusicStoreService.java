package com.example.musicstoredemo.service;

import com.example.musicstoredemo.exception.ItemNotFoundException;
import com.example.musicstoredemo.model.Accessory;
import com.example.musicstoredemo.model.Guitar;
import com.example.musicstoredemo.model.catalog.Caretaker;
import com.example.musicstoredemo.model.catalog.Catalog;
import com.example.musicstoredemo.model.price.Currency;
import com.example.musicstoredemo.model.price.Price;
import com.example.musicstoredemo.model.price.PriceAdapter;
import com.example.musicstoredemo.model.price.PriceInEuros;
import com.example.musicstoredemo.repository.AccessoryRepository;
import com.example.musicstoredemo.repository.GuitarRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class MusicStoreService {

    @Autowired
    private GuitarRepository guitarRepository;

    @Autowired
    private AccessoryRepository accessoryRepository;

    private Catalog catalog;
    private Caretaker caretaker;

    private Price priceAdapter;
    private PriceInEuros priceInEuros;

    @PostConstruct
    private void setUp() {
        catalog = Catalog.getInstance();
        caretaker = new Caretaker();
        populateCatalog();
    }

    public List<Guitar> getGuitarCatalog(Currency currency) {
        return currency == Currency.euro ? catalog.getGuitarList()
                                         : convertPrices(currency);
    }

    public List<Accessory> getAccessoriesCatalog() {
        return catalog.getAccessoryList();
    }

    public Guitar getGuitarById(long id) {
        return catalog.getGuitarList().stream()
                .filter(g -> g.getId() == id).findFirst()
                .orElseThrow(() -> new ItemNotFoundException(String.format("No guitar with id: %s found", id)));
    }

    public Accessory getAccessoryById(long id) {
        return catalog.getAccessoryList().stream()
                .filter(a -> a.getId() == id).findFirst()
                .orElseThrow(() -> new ItemNotFoundException(String.format("No accessory with id: %s found", id)));
    }

    public void addGuitar(Guitar guitar) {
        if (!guitarRepository.findAll().contains(guitar)) {
            guitarRepository.save(guitar);
            refreshCatalog();
        }
    }

    public void addAccessory(Accessory accessory) {
        if (!accessoryRepository.findAll().contains(accessory)) {
            accessoryRepository.save(accessory);
            refreshCatalog();
        }
    }

    public void revertCatalog() {
        caretaker.revert(catalog);
    }

    private List<Guitar> convertPrices(Currency currency){
        List<Guitar> guitarsWithUpdatedPrices = new ArrayList<Guitar>();

        if(!catalog.getGuitarList().isEmpty()) {
            catalog.getGuitarList().forEach(g -> {
                priceInEuros = new PriceInEuros(g.getPrice());
                priceAdapter = new PriceAdapter(priceInEuros);
                guitarsWithUpdatedPrices.add(new Guitar(g.getId(),
                                                        g.getBrand(),
                                                        g.getType(),
                                                        g.getModel(),
                                                        currency == Currency.dollar
                                                                ? priceAdapter.getPriceInDollars()
                                                                : priceAdapter.getPriceInPounds()));
            });
        }

        return guitarsWithUpdatedPrices;
    }

    private void refreshCatalog() {
        log.info("Saving current version and refreshing catalog...");
        caretaker.save(catalog);
        populateCatalog();
    }

    private void populateCatalog() {
        catalog.setGuitarList(guitarRepository.findAll());
        catalog.setAccessoryList(accessoryRepository.findAll());
    }

}
