package com.home.inventory.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.home.inventory.entities.Categories;
import com.home.inventory.repository.CategoriesRepository;
import com.home.inventory.util.ConstraintsValidator;

@Service
public class CategoriesService implements ICategoriesService {

    private static final Logger LOGGER = LoggerFactory
            .getLogger("CategoriesService");

    @Autowired
    private CategoriesRepository categoriesRepository;

    public Categories addCategory(final Categories category) {

        if ((ConstraintsValidator.checkValidCategory(category) == null)
                || (categoriesRepository.findCategoriesByDescription(
                        category.getDescription()) != null)) {
            return null;
        }
        categoriesRepository.save(category);
        LOGGER.info("Succes new user acccount creation");
        return category;
    }

    public Categories getCategoryByDescription(final String description) {
        if (categoriesRepository
                .findCategoriesByDescription(description) == null) {
            return null;
        }
        return categoriesRepository.findCategoriesByDescription(description);
    }

    public Categories getCategoryById(final Categories category) {
        return categoriesRepository.findById(category.getId()).orElse(null);
    }

}
