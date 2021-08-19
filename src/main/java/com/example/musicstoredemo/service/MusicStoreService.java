package com.example.musicstoredemo.service;

import com.example.musicstoredemo.exception.ItemNotFoundException;
import com.example.musicstoredemo.model.Accessory;
import com.example.musicstoredemo.model.Catalog;
import com.example.musicstoredemo.model.Guitar;
import com.example.musicstoredemo.repository.AccessoryRepository;
import com.example.musicstoredemo.repository.GuitarRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Slf4j
@Service
public class MusicStoreService {

    @Autowired
    private GuitarRepository guitarRepository;

    @Autowired
    private AccessoryRepository accessoryRepository;

    private Catalog catalog;

    @PostConstruct
    private void setUp() {
        catalog = Catalog.getInstance();
    }

    public List<Guitar> getGuitarCatalog() {
        return catalog.getGuitarList();
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

    @Scheduled(fixedDelayString = "${catalog.refresh-catalog-rate}")
    private void refreshCatalog() {
        log.info("Refreshing catalog...");
        catalog.setGuitarList(guitarRepository.findAll());
        catalog.setAccessoryList(accessoryRepository.findAll());
    }
}
