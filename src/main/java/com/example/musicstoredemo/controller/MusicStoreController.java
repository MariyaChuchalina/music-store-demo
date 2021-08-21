package com.example.musicstoredemo.controller;

import com.example.musicstoredemo.model.access.Endpoint;
import com.example.musicstoredemo.model.catalog.items.Accessory;
import com.example.musicstoredemo.model.catalog.items.Guitar;
import com.example.musicstoredemo.service.MusicStoreService;
import com.example.musicstoredemo.service.UserService;
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

    private static final String ACCESS_HEADER = "access-token";

    @Autowired
    private MusicStoreService musicStoreService;

    @Autowired
    private UserService userService;

    @GetMapping("/guitars")
    public List<Guitar> getGuitarCatalog(@RequestParam(value = "currency", defaultValue = "euro") String currency,
                                         @RequestHeader(value = ACCESS_HEADER) String accessHeader) {
        userService.validateAccess(accessHeader, Endpoint.GET_GUITAR_CATALOGUE);
        return musicStoreService.getGuitarCatalog(currency);
    }

    @GetMapping("/accessories")
    public List<Accessory> getAccessoriesCatalog(@RequestParam(value = "currency", defaultValue = "euro") String currency,
                                                 @RequestHeader(value = ACCESS_HEADER) String accessHeader) {
        userService.validateAccess(accessHeader, Endpoint.GET_ACCESSORIES_CATALOGUE);
        return musicStoreService.getAccessoriesCatalog(currency);
    }

    @GetMapping("/guitar")
    public Guitar getGuitarById(@RequestParam @NotBlank long id,
                                @RequestParam(value = "currency", defaultValue = "euro") String currency,
                                @RequestHeader(value = ACCESS_HEADER) String accessHeader) {
        userService.validateAccess(accessHeader, Endpoint.GET_GUITAR_BY_ID);
        return musicStoreService.getGuitarById(id, currency);
    }

    @GetMapping("/accessory")
    public Accessory getAccessoryById(@RequestParam @NotBlank long id,
                                      @RequestParam(value = "currency", defaultValue = "euro") String currency,
                                      @RequestHeader(value = ACCESS_HEADER) String accessHeader) {
        userService.validateAccess(accessHeader, Endpoint.GET_ACCESSORY_BY_ID);
        return musicStoreService.getAccessoryById(id, currency);
    }

    @PostMapping("/revert")
    public void revertCatalog(@RequestHeader(value = ACCESS_HEADER) String accessHeader) {
        userService.validateAccess(accessHeader, Endpoint.POST_REVERT_CATALOGUE);
        musicStoreService.revertCatalog();
    }

    @PostMapping("/guitar")
    public void addGuitar(@RequestBody Guitar guitar,
                          @RequestHeader(value = ACCESS_HEADER) String accessHeader) {
        userService.validateAccess(accessHeader, Endpoint.POST_ADD_GUITAR);
        musicStoreService.addGuitar(guitar);
    }

    @PostMapping("/accessory")
    public void addAccessory(@RequestBody Accessory accessory,
                             @RequestHeader(value = ACCESS_HEADER) String accessHeader) {
        userService.validateAccess(accessHeader, Endpoint.POST_ADD_ACCESSORY);
        musicStoreService.addAccessory(accessory);
    }

}
