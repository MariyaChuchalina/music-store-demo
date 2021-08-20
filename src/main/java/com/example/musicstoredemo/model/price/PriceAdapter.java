package com.example.musicstoredemo.model.price;

import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;

@AllArgsConstructor
public class PriceAdapter implements Price {

    PriceInEuros priceInEuros;

    @Override
    public double getPriceInDollars() {
        return BigDecimal.valueOf(priceInEuros.getAmount() * 1.17)
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
    }

    @Override
    public double getPriceInPounds() {
        return BigDecimal.valueOf(priceInEuros.getAmount() * 0.86)
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
    }
}
