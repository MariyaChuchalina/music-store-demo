package com.example.musicstoredemo.model.catalog;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "accessories")
public class Accessory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String brand;

}
