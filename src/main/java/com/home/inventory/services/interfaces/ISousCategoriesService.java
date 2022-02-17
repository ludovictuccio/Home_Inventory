package com.home.inventory.services.interfaces;

import com.home.inventory.entities.SousCategories;

public interface ISousCategoriesService {

    /**
     * Method used to add new sous-category.
     * 
     * @param sousCategory
     * @return SousCategories
     */
    SousCategories addSousCategory(SousCategories sousCategory);

    /**
     * Method used to get sous-category by ID.
     * 
     * @param id
     * @return SousCategories
     */
    SousCategories getSousCategoryById(Long id);

    /**
     * Method used to update sous-category by description.
     * 
     * @param sousCategory
     * @param description
     * @return boolean
     */
    boolean updateSousCategoriesByDescription(SousCategories sousCategory,
            String description);

    /**
     * Method used to update sous-category by ID.
     * 
     * @param sousCategory
     * @param id
     * @return boolean
     */
    boolean updateSousCategoryById(SousCategories sousCategory, Long id);

    /**
     * Method used to delete sous-category by ID.
     * 
     * @param categoryId
     * @return boolean
     */
    boolean deleteSousCategoryById(Long categoryId);

}
