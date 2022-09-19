package com.home.inventory.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.home.inventory.entities.Categories;
import com.home.inventory.repository.CategoriesRepository;
import com.home.inventory.services.interfaces.ICategoriesService;
import com.home.inventory.util.ConstraintsValidator;

@Service
public class CategoriesService implements ICategoriesService {

    private static final Logger LOGGER = LoggerFactory
            .getLogger("CategoriesService");

    @Autowired
    private CategoriesRepository categoriesRepository;

    /**
     * {@inheritDoc}
     */
    public Categories addCategory(Categories category) {

        if ((ConstraintsValidator.checkValidCategory(category) == null)
                || (categoriesRepository.findCategoriesByDescription(
                        category.getDescription()) != null)) {
            return null;
        }
        categoriesRepository.save(category);
        LOGGER.info("Succes new category creation");
        return category;
    }

    /**
     * {@inheritDoc}
     */
    public Categories getCategoryById(final Long id) {
        return categoriesRepository.findById(id).orElse(null);
    }

    /**
     * {@inheritDoc}
     */
    public boolean updateCategoriesByDescription(final Categories category,
            final String description) {
        boolean isUpdated = false;

        Categories existingCategory = categoriesRepository
                .findCategoriesByDescription(description);

        if (existingCategory == null) {
            LOGGER.error("Unknow category: {}", description);
            return isUpdated;
        }
        existingCategory.setDescription(category.getDescription());
        categoriesRepository.save(existingCategory);
        isUpdated = true;
        return isUpdated;
    }

    /**
     * {@inheritDoc}
     */
    public boolean updateCategoryById(final Categories category,
            final Long id) {
        boolean isUpdated = false;

        Categories existingCategory = categoriesRepository.findById(id)
                .orElse(null);

        if (existingCategory == null) {
            LOGGER.error("Unknow category for id: {}", id);
            return isUpdated;
        }
        existingCategory.setDescription(category.getDescription());
        categoriesRepository.save(existingCategory);
        isUpdated = true;
        return isUpdated;
    }

    /**
     * {@inheritDoc}
     */
    public boolean deleteCategoryById(final Long categoryId) {
        boolean isDeleted = false;

        Categories existingCategory = categoriesRepository.findById(categoryId)
                .orElse(null);

        if (existingCategory == null) {
            LOGGER.error("Unknow category for id: {}", categoryId);
            return isDeleted;
        }
        categoriesRepository.delete(existingCategory);
        isDeleted = true;
        return isDeleted;
    }

}
