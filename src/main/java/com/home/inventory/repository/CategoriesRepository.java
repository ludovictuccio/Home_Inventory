package com.home.inventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.home.inventory.entities.Categories;

@Repository
public interface CategoriesRepository extends JpaRepository<Categories, Long> {

    Categories findCategoriesById(Long categoriesId);

    Categories findCategoriesByDescription(String description);

}
