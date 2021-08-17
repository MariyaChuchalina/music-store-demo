package com.example.musicstoredemo.controller;

import com.example.musicstoredemo.config.HeaderConfig;
import com.example.musicstoredemo.model.Accessory;
import com.example.musicstoredemo.model.Guitar;
import com.example.musicstoredemo.service.MusicStoreService;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.util.List;

@RestController
@RequestMapping("/catalog")
public class MusicStoreController {

    @Autowired
    private MusicStoreService musicStoreService;

    @Autowired
    private HeaderConfig headerConfig;

    @GetMapping("/guitars")
    public List<Guitar> getGuitarCatalog() {
        return musicStoreService.getGuitarCatalog();
    }

    @GetMapping("/accessories")
    public List<Accessory> getAccessoriesCatalog() {
        return musicStoreService.getAccessoriesCatalog();
    }

    @GetMapping("/guitar")
    public Guitar getGuitarById(@RequestParam @NotBlank long id) {
        return musicStoreService.getGuitarById(id);
    }

    @GetMapping("/accessory")
    public Accessory getAccessoryById(@RequestParam @NotBlank long id) {
        return musicStoreService.getAccessoryById(id);
    }

    @PostMapping("/guitar")
    public void addGuitar(@RequestBody Guitar guitar,
                          @RequestHeader String accessHeader) throws IllegalAccessException {
        validateHeader(accessHeader);
        musicStoreService.addGuitar(guitar);
    }

    @PostMapping("/accessory")
    public void addAccessory(@RequestBody Accessory accessory,
                             @RequestHeader String accessHeader) throws IllegalAccessException {
        validateHeader(accessHeader);
        musicStoreService.addAccessory(accessory);
    }

    private void validateHeader(String accessHeader) throws IllegalAccessException {
        if (!accessHeader.equals(headerConfig.getAccessHeader())) {
            throw new IllegalAccessException("Access violation");
        }
    }
}
