package com.example.musicstoredemo.controller;

import com.example.musicstoredemo.config.HeaderConfig;
import com.example.musicstoredemo.model.Accessory;
import com.example.musicstoredemo.model.Guitar;
import com.example.musicstoredemo.model.price.Currency;
import com.example.musicstoredemo.service.MusicStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
import java.util.List;

@RestController
@RequestMapping("/catalog")
public class MusicStoreController {

    private static final String ACCESS_HEADER = "modify-access";

    @Autowired
    private MusicStoreService musicStoreService;

    @Autowired
    private HeaderConfig headerConfig;

    @GetMapping("/guitars")
    public List<Guitar> getGuitarCatalog(@RequestParam(value = "currency", defaultValue = "euro") Currency currency) {
        return musicStoreService.getGuitarCatalog(currency);
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

    @PostMapping("/revert")
    public void revertCatalog(@RequestHeader(value = ACCESS_HEADER) String accessHeader) throws IllegalAccessException {
        validateHeader(accessHeader);
        musicStoreService.revertCatalog();
    }

    @PostMapping("/guitar")
    public void addGuitar(@RequestBody Guitar guitar,
                          @RequestHeader(value = ACCESS_HEADER) String accessHeader) throws IllegalAccessException {
        validateHeader(accessHeader);
        musicStoreService.addGuitar(guitar);
    }

    @PostMapping("/accessory")
    public void addAccessory(@RequestBody Accessory accessory,
                             @RequestHeader(value = ACCESS_HEADER) String accessHeader) throws IllegalAccessException {
        validateHeader(accessHeader);
        musicStoreService.addAccessory(accessory);
    }

    private void validateHeader(String accessHeader) throws IllegalAccessException {
        if (!accessHeader.equals(headerConfig.getAccessHeader())) {
            throw new IllegalAccessException("Access violation");
        }
    }

}
