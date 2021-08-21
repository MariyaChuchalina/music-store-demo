package com.example.musicstoredemo.service;

import com.example.musicstoredemo.exception.ItemNotFoundException;
import com.example.musicstoredemo.model.catalog.Caretaker;
import com.example.musicstoredemo.model.catalog.Catalog;
import com.example.musicstoredemo.model.catalog.items.Accessory;
import com.example.musicstoredemo.model.catalog.items.Guitar;
import com.example.musicstoredemo.model.price.Currency;
import com.example.musicstoredemo.model.price.Price;
import com.example.musicstoredemo.model.price.PriceAdapter;
import com.example.musicstoredemo.model.price.PriceInEuros;
import com.example.musicstoredemo.model.sort.Brand;
import com.example.musicstoredemo.model.sort.BrandSorter;
import com.example.musicstoredemo.model.sort.PriceAscendingSorter;
import com.example.musicstoredemo.model.sort.PriceDescendingSorter;
import com.example.musicstoredemo.model.sort.SortContext;
import com.example.musicstoredemo.model.sort.SortStrategy;
import com.example.musicstoredemo.model.sort.TypeSorter;
import com.example.musicstoredemo.repository.AccessoryRepository;
import com.example.musicstoredemo.repository.GuitarRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class MusicStoreService {

    @Autowired
    private GuitarRepository guitarRepository;

    @Autowired
    private AccessoryRepository accessoryRepository;

    private Catalog catalog;
    private Caretaker caretaker;

    private List<Guitar> updatedGuitarCatalog;
    private List<Accessory> updatedAccessoriesCatalog;
    private Price priceAdapter;
    private PriceInEuros priceInEuros;

    private SortContext sortContext;

    @PostConstruct
    private void setUp() {
        catalog = Catalog.getInstance();
        caretaker = new Caretaker();
        populateCatalog();
        updatedGuitarCatalog = new ArrayList<Guitar>();
        updatedAccessoriesCatalog = new ArrayList<Accessory>();
        sortContext = new SortContext();
    }

    public List<Guitar> getGuitarCatalog(String currency, String sort) {
        updatedGuitarCatalog.clear();

        catalog.getGuitarList().forEach(g -> updatedGuitarCatalog.add(new Guitar(g.getId(),
                                                                                 g.getBrand(),
                                                                                 g.getType(),
                                                                                 g.getModel(),
                                                                                 g.getPrice())));

        switch (SortStrategy.parse(sort)) {
            case SORT_BY_BRAND: {
                sortContext.setSorter(new BrandSorter());
                sortContext.sortGuitars(updatedGuitarCatalog);
                break;
            }
            case SORT_BY_TYPE: {
                sortContext.setSorter(new TypeSorter());
                sortContext.sortGuitars(updatedGuitarCatalog);
                break;
            }
            case SORT_BY_PRICE_ASCENDING: {
                sortContext.setSorter(new PriceAscendingSorter());
                sortContext.sortGuitars(updatedGuitarCatalog);
                break;
            }
            case SORT_BY_PRICE_DESCENDING: {
                sortContext.setSorter(new PriceDescendingSorter());
                sortContext.sortGuitars(updatedGuitarCatalog);
                break;
            }
            case NONE:
            default:
                break;
        }

        return Currency.parse(currency) == Currency.EURO ? updatedGuitarCatalog
                                                         : convertGuitarPrices(currency);
    }

    public List<Accessory> getAccessoriesCatalog(String currency, String sort) {
        updatedAccessoriesCatalog.clear();

        catalog.getAccessoryList().forEach(a -> updatedAccessoriesCatalog.add(new Accessory(a.getId(),
                                                                                            a.getBrand(),
                                                                                            a.getType(),
                                                                                            a.getPrice())));

        switch (SortStrategy.parse(sort)) {
            case SORT_BY_BRAND: {
                sortContext.setSorter(new BrandSorter());
                sortContext.sortAccessories(updatedAccessoriesCatalog);
                break;
            }
            case SORT_BY_TYPE: {
                sortContext.setSorter(new TypeSorter());
                sortContext.sortAccessories(updatedAccessoriesCatalog);
                break;
            }
            case SORT_BY_PRICE_ASCENDING: {
                sortContext.setSorter(new PriceAscendingSorter());
                sortContext.sortAccessories(updatedAccessoriesCatalog);
                break;
            }
            case SORT_BY_PRICE_DESCENDING: {
                sortContext.setSorter(new PriceDescendingSorter());
                sortContext.sortAccessories(updatedAccessoriesCatalog);
                break;
            }
            case NONE:
            default:
                break;
        }

        return Currency.parse(currency) == Currency.EURO ? updatedAccessoriesCatalog
                                                         : convertAccessoryPrices(currency);
    }

    public List<Guitar> getGuitarCatalogByBrand(String brand, String currency, String sort) {
        if(Brand.parse(brand) == null){
            throw new ItemNotFoundException("Invalid brand requested");
        }

        return getGuitarCatalog(currency, sort).stream()
                                               .filter(g -> g.getBrand().equalsIgnoreCase(brand))
                                               .collect(Collectors.toList());
    }

    public List<Accessory> getAccessoriesCatalogByBrand(String brand, String currency, String sort) {
        if(Brand.parse(brand) == null){
            throw new ItemNotFoundException("Invalid brand requested");
        }

        return getAccessoriesCatalog(currency, sort).stream()
                                                    .filter(a -> a.getBrand().equalsIgnoreCase(brand))
                                                    .collect(Collectors.toList());
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

        switch (Currency.parse(currency)) {
            case DOLLAR: {
                guitarToReturn.setPrice(priceAdapter.getPriceInDollars());
                break;
            }
            case POUND: {
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

        switch (Currency.parse(currency)) {
            case DOLLAR: {
                accessoryToReturn.setPrice(priceAdapter.getPriceInDollars());
                break;
            }
            case POUND: {
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
        log.info("[<-] Reverting catalogue to it's previous state...");
        caretaker.revert(catalog);
    }

    private List<Guitar> convertGuitarPrices(String currency) {
        updatedGuitarCatalog.forEach(g -> {
            priceInEuros = new PriceInEuros(g.getPrice());
            priceAdapter = new PriceAdapter(priceInEuros);
            g.setPrice(Currency.parse(currency) == Currency.DOLLAR
                                                ? priceAdapter.getPriceInDollars()
                                                : priceAdapter.getPriceInPounds());
        });

        return updatedGuitarCatalog;
    }

    private List<Accessory> convertAccessoryPrices(String currency) {
        updatedAccessoriesCatalog.forEach(a -> {
            priceInEuros = new PriceInEuros(a.getPrice());
            priceAdapter = new PriceAdapter(priceInEuros);
            a.setPrice(Currency.parse(currency) == Currency.DOLLAR
                                                ? priceAdapter.getPriceInDollars()
                                                : priceAdapter.getPriceInPounds());
        });

        return updatedAccessoriesCatalog;
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
