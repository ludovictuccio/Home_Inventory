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

        if ((ConstraintsValidator.checkExistingCategory(category) == null)
                || (categoriesRepository.findCategoriesByDescription(
                        category.getDescription()) != null)) {
            return null;
        }
        categoriesRepository.save(category);
        LOGGER.info("Succes new user acccount creation");
        return category;
    }

}
