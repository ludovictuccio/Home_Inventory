package com.home.inventory.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.home.inventory.entities.SousCategories;
import com.home.inventory.repository.SousCategoriesRepository;
import com.home.inventory.services.interfaces.ISousCategoriesService;
import com.home.inventory.util.ConstraintsValidator;

@Service
public class SousCategoriesService implements ISousCategoriesService {

    private static final Logger LOGGER = LoggerFactory
            .getLogger("SousCategoriesService");

    @Autowired
    private SousCategoriesRepository sousCategoriesRepository;

    /**
     * {@inheritDoc}
     */
    public SousCategories addSousCategory(SousCategories sousCategory) {

        if ((ConstraintsValidator.checkValidSousCategory(sousCategory) == null)
                || (sousCategoriesRepository.findSousCategoriesByDescription(
                        sousCategory.getDescription()) != null)) {
            return null;
        }
        sousCategoriesRepository.save(sousCategory);
        LOGGER.info("Succes new sousCategory creation");
        return sousCategory;
    }

    /**
     * {@inheritDoc}
     */
    public SousCategories getSousCategoryById(final Long id) {
        return sousCategoriesRepository.findById(id).orElse(null);
    }

    /**
     * {@inheritDoc}
     */
    public boolean updateSousCategoriesByDescription(
            final SousCategories sousCategory, final String description) {
        boolean isUpdated = false;

        SousCategories existingSousCategory = sousCategoriesRepository
                .findSousCategoriesByDescription(description);

        if (existingSousCategory == null) {
            LOGGER.error("Unknow sousCategory: {}", description);
            return isUpdated;
        }
        existingSousCategory.setDescription(sousCategory.getDescription());
        sousCategoriesRepository.save(existingSousCategory);
        isUpdated = true;
        return isUpdated;
    }

    /**
     * {@inheritDoc}
     */
    public boolean updateSousCategoryById(final SousCategories sousCategory,
            final Long id) {
        boolean isUpdated = false;

        SousCategories existingSousCategory = sousCategoriesRepository
                .findById(id).orElse(null);

        if (existingSousCategory == null) {
            LOGGER.error("Unknow souscategory for id: {}", id);
            return isUpdated;
        }
        existingSousCategory.setDescription(sousCategory.getDescription());
        sousCategoriesRepository.save(existingSousCategory);
        isUpdated = true;
        return isUpdated;
    }

    /**
     * {@inheritDoc}
     */
    public boolean deleteSousCategoryById(final Long categoryId) {
        boolean isDeleted = false;

        SousCategories existingSousCategory = sousCategoriesRepository
                .findById(categoryId).orElse(null);

        if (existingSousCategory == null) {
            LOGGER.error("Unknow sous-category for id: {}", categoryId);
            return isDeleted;
        }
        sousCategoriesRepository.delete(existingSousCategory);
        isDeleted = true;
        return isDeleted;
    }

}
