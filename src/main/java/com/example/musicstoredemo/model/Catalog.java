package com.example.musicstoredemo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Catalog {

    private List<Guitar> guitarList;
    private List<Accessory> accessoryList;
}
