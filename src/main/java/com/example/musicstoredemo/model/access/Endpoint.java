package com.example.musicstoredemo.model.access;

import com.example.musicstoredemo.model.user.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Endpoint {

    GET_GUITAR_CATALOGUE(UserRole.CLIENT),
    GET_GUITAR_CATALOGUE_BY_BRAND(UserRole.CLIENT),
    GET_ACCESSORIES_CATALOGUE(UserRole.CLIENT),
    GET_ACCESSORIES_CATALOGUE_BY_BRAND(UserRole.CLIENT),
    GET_GUITAR_BY_ID(UserRole.CLIENT),
    GET_ACCESSORY_BY_ID(UserRole.CLIENT),
    POST_REVERT_CATALOGUE(UserRole.ADMIN),
    POST_ADD_GUITAR(UserRole.ADMIN),
    POST_ADD_ACCESSORY(UserRole.ADMIN);

    private UserRole minimalRole;

}
