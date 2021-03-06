package com.example.musicstoredemo.repository;

import com.example.musicstoredemo.model.catalog.items.Accessory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccessoryRepository extends JpaRepository<Accessory, Long> {
}
