package com.example.musicstoredemo.service;

import com.example.musicstoredemo.exception.ItemNotFoundException;
import com.example.musicstoredemo.model.catalog.items.Accessory;
import com.example.musicstoredemo.model.catalog.Caretaker;
import com.example.musicstoredemo.model.catalog.Catalog;
import com.example.musicstoredemo.model.catalog.items.Guitar;
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

    List<Guitar> guitarsWithUpdatedPrices;
    List<Accessory> accessoriesWithUpdatedPrices;
    private Price priceAdapter;
    private PriceInEuros priceInEuros;

    @PostConstruct
    private void setUp() {
        catalog = Catalog.getInstance();
        caretaker = new Caretaker();
        populateCatalog();
        guitarsWithUpdatedPrices = new ArrayList<Guitar>();
        accessoriesWithUpdatedPrices = new ArrayList<Accessory>();
    }

    public List<Guitar> getGuitarCatalog(String currency) {
        return Currency.parse(currency) == Currency.EURO ? catalog.getGuitarList()
                                                         : convertGuitarPrices(currency);
    }

    public List<Accessory> getAccessoriesCatalog(String currency) {
        return Currency.parse(currency) == Currency.EURO ? catalog.getAccessoryList()
                                                         : convertAccessoryPrices(currency);
    }

    public Guitar getGuitarById(long id, String currency) {
        Guitar guitar = catalog.getGuitarList().stream()
                        .filter(g -> g.getId() == id).findFirst()
                        .orElseThrow(() -> new ItemNotFoundException(String.format("No guitar with id: %s found", id)));

        Guitar guitarToReturn = new Guitar(guitar.getId(), guitar.getBrand(), guitar.getType(), guitar.getModel(), guitar.getPrice());

        if (Currency.parse(currency) != Currency.EURO) {
            priceInEuros = new PriceInEuros(guitarToReturn.getPrice());
            priceAdapter = new PriceAdapter(priceInEuros);
        }

        switch(Currency.parse(currency)){
            case DOLLAR:{
                guitarToReturn.setPrice(priceAdapter.getPriceInDollars());
                break;
            }
            case POUND:{
                guitarToReturn.setPrice(priceAdapter.getPriceInPounds());
                break;
            }
            default:
                break;
        }

        return guitarToReturn;
    }

    public Accessory getAccessoryById(long id, String currency) {
        Accessory accessory = catalog.getAccessoryList().stream()
                              .filter(a -> a.getId() == id).findFirst()
                              .orElseThrow(() -> new ItemNotFoundException(String.format("No accessory with id: %s found", id)));

        Accessory accessoryToReturn = new Accessory(accessory.getId(), accessory.getBrand(), accessory.getType(), accessory.getPrice());

        if (Currency.parse(currency) != Currency.EURO) {
            priceInEuros = new PriceInEuros(accessoryToReturn.getPrice());
            priceAdapter = new PriceAdapter(priceInEuros);
        }

        switch(Currency.parse(currency)){
            case DOLLAR:{
                accessoryToReturn.setPrice(priceAdapter.getPriceInDollars());
                break;
            }
            case POUND:{
                accessoryToReturn.setPrice(priceAdapter.getPriceInPounds());
                break;
            }
            default:
                break;
        }

        return accessoryToReturn;
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
        log.info("[<-] Reverting catalogue to it\'s previous state...");
        caretaker.revert(catalog);
    }

    private List<Guitar> convertGuitarPrices(String currency){
        guitarsWithUpdatedPrices.clear();

        if(!catalog.getGuitarList().isEmpty()) {
            catalog.getGuitarList().forEach(g -> {
                priceInEuros = new PriceInEuros(g.getPrice());
                priceAdapter = new PriceAdapter(priceInEuros);
                guitarsWithUpdatedPrices.add(new Guitar(g.getId(),
                                                        g.getBrand(),
                                                        g.getType(),
                                                        g.getModel(),
                                                        Currency.parse(currency) == Currency.DOLLAR
                                                                                ? priceAdapter.getPriceInDollars()
                                                                                : priceAdapter.getPriceInPounds()));
            });
        }

        return guitarsWithUpdatedPrices;
    }

    private List<Accessory> convertAccessoryPrices(String currency){
        accessoriesWithUpdatedPrices.clear();

        if(!catalog.getAccessoryList().isEmpty()) {
            catalog.getAccessoryList().forEach(a -> {
                priceInEuros = new PriceInEuros(a.getPrice());
                priceAdapter = new PriceAdapter(priceInEuros);
                accessoriesWithUpdatedPrices.add(new Accessory(a.getId(),
                                                               a.getBrand(),
                                                               a.getType(),
                                                               Currency.parse(currency) == Currency.DOLLAR
                                                                                        ? priceAdapter.getPriceInDollars()
                                                                                        : priceAdapter.getPriceInPounds()));
            });
        }

        return accessoriesWithUpdatedPrices;
    }

    private void refreshCatalog() {
        log.info("[->] Saving current version and refreshing catalog...");
        caretaker.save(catalog);
        populateCatalog();
    }

    private void populateCatalog() {
        catalog.setGuitarList(guitarRepository.findAll());
        catalog.setAccessoryList(accessoryRepository.findAll());
    }

}
