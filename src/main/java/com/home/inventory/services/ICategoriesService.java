package com.home.inventory.services;

import com.home.inventory.entities.Categories;

public interface ICategoriesService {

    Categories addCategory(Categories category);

    Categories getCategoryByDescription(String description);

    Categories getCategoryById(Categories category);
}
