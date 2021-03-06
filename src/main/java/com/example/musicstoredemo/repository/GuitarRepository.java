package com.example.musicstoredemo.repository;

import com.example.musicstoredemo.model.catalog.items.Guitar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GuitarRepository extends JpaRepository<Guitar, Long> {
}
