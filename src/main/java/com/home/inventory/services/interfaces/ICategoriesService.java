package com.home.inventory.services.interfaces;

import com.home.inventory.entities.Categories;

public interface ICategoriesService {

    /**
     * Method used to add new category.
     * 
     * @param category
     * @return Categories
     */
    Categories addCategory(Categories category);

    /**
     * Method used to get category by ID.
     * 
     * @param category
     * @return Categories
     */
    Categories getCategoryById(Long id);

    /**
     * Method used to update category by description.
     * 
     * @param category
     * @return boolean
     */
    boolean updateCategoriesByDescription(Categories category,
            String description);

    /**
     * Method used to update category by ID.
     * 
     * @param category
     * @param id
     * @return boolean
     */
    boolean updateCategoryById(Categories category, Long id);

    /**
     * Method used to delete category by ID.
     * 
     * @param categoryId
     * @return boolean
     */
    boolean deleteCategoryById(final Long categoryId);
}
