package com.home.inventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.home.inventory.entities.SousCategories;

@Repository
public interface SousCategoriesRepository
        extends JpaRepository<SousCategories, Long> {

    SousCategories findSousCategoriesById(Long SousCategoriesId);

}
