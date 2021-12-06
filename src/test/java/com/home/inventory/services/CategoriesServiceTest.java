package com.home.inventory.services;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import com.home.inventory.entities.Categories;
import com.home.inventory.repository.CategoriesRepository;

@SpringBootTest
@TestPropertySource(locations = "classpath:application.properties")
public class CategoriesServiceTest {

    @Autowired
    public ICategoriesService categorieService;

    @Autowired
    private CategoriesRepository categoriesRepository;

    @BeforeEach
    public void setUpPerTest() {
        categoriesRepository.deleteAllInBatch();
        categoriesRepository.findAll().clear();
    }

    @Test
    @Tag("POST")
    @DisplayName("Add new category - OK")
    public void givenCategory_whenAdd_thenReturnSaved() {
        // GIVEN
        Categories category = new Categories("Maison");

        // WHEN
        Categories result = categorieService.addCategory(category);

        // THEN
        assertThat(result).isNotNull();
        assertThat(result.getId()).isNotNull();
        assertThat(result.getDescription()).isEqualTo("Maison");
    }

    @Test
    @Tag("POST")
    @DisplayName("Add new category - ERROR - Category already exists (same description)")
    public void givenExistingCategory_whenAddNewCategoryWithSameDescription_thenReturnNull() {
        // GIVEN
        categoriesRepository.save(new Categories("Maison"));
        assertThat(categoriesRepository.findAll().size()).isEqualTo(1);

        // WHEN
        Categories result = categorieService
                .addCategory(new Categories("Maison"));

        // THEN
        assertThat(result).isNull();
        assertThat(categoriesRepository.findAll().size()).isEqualTo(1);
    }

    @Test
    @Tag("POST")
    @DisplayName("Add new category - ERROR - Empty description")
    public void givenZeroCategory_whenAddWithEmptyDescription_thenReturnNull() {
        // GIVEN
        // WHEN
        Categories result = categorieService.addCategory(new Categories(""));

        // THEN
        assertThat(result).isNull();
        assertThat(categoriesRepository.findAll().size()).isEqualTo(0);
    }

    @Test
    @Tag("GET")
    @DisplayName("Get by description - OK - MAJUSCULES")
    public void givenCategory_whenGetByDescriptionWithMajuscules_thenReturnOk() {
        // GIVEN
        categoriesRepository.save(new Categories("Maison"));

        // WHEN
        Categories result = categorieService.getCategoryByDescription("mAISON");

        // THEN
        assertThat(result).isNotNull();
        assertThat(result.getId()).isNotNull();
        assertThat(result.getDescription()).isEqualTo("Maison");
    }

    @Test
    @Tag("GET")
    @DisplayName("Get by ID - OK ")
    public void givenCategory_whenGetById_thenReturnOk() {
        // GIVEN
        categoriesRepository.save(new Categories("Maison"));

        // WHEN
        Categories result = categorieService
                .getCategoryById(categoriesRepository.findAll().get(0).getId());

        // THEN
        assertThat(result).isNotNull();
        assertThat(result.getId()).isNotNull();
        assertThat(result.getDescription()).isEqualTo("Maison");
    }

    @Test
    @Tag("PUT")
    @DisplayName("Update by description - OK")
    public void givenCategory_whenUpdateByDescription_thenReturnOk() {
        // GIVEN
        categoriesRepository.save(new Categories("Maison"));

        Categories categoryToUpdate = new Categories("Maisons");

        // WHEN
        boolean isUpdated = categorieService
                .updateCategoriesByDescription(categoryToUpdate, "Maison");

        // THEN
        assertThat(isUpdated).isTrue();
        assertThat(categoriesRepository.findAll().size()).isEqualTo(1);
        assertThat(categoriesRepository.findAll().get(0).getDescription())
                .isEqualTo("Maisons");
    }

    @Test
    @Tag("PUT")
    @DisplayName("Update by ID - OK")
    public void givenCategory_whenUpdateById_thenReturnOk() {
        // GIVEN
        categoriesRepository.save(new Categories("Maison"));

        Categories categoryToUpdate = new Categories("Maisons");

        Long categoryId = categoriesRepository.findAll().get(0).getId();

        // WHEN
        boolean isUpdated = categorieService
                .updateCategoryById(categoryToUpdate, categoryId);

        // THEN
        assertThat(isUpdated).isTrue();
        assertThat(categoriesRepository.findAll().size()).isEqualTo(1);
        assertThat(categoriesRepository.findAll().get(0).getDescription())
                .isEqualTo("Maisons");
    }

    @Test
    @Tag("DELETE")
    @DisplayName("Delete by ID - OK")
    public void givenCategory_whenDeleteWithHisId_thenReturnTrue() {
        // GIVEN
        categoriesRepository.save(new Categories("Maison"));
        assertThat(categoriesRepository.findAll().size()).isEqualTo(1);

        Long categoryId = categoriesRepository.findAll().get(0).getId();

        // WHEN
        boolean isDeleted = categorieService.deleteCategoryById(categoryId);

        // THEN
        assertThat(isDeleted).isTrue();
        assertThat(categoriesRepository.findAll().size()).isEqualTo(0);
    }

    @Test
    @Tag("DELETE")
    @DisplayName("Delete by ID - Error - Unkow id")
    public void givenCategory_whenDeleteWithUnknowId_thenReturnFalse() {
        // GIVEN
        categoriesRepository.save(new Categories("Maison"));
        assertThat(categoriesRepository.findAll().size()).isEqualTo(1);

        // WHEN
        boolean isDeleted = categorieService.deleteCategoryById(9999L);

        // THEN
        assertThat(isDeleted).isFalse();
        assertThat(categoriesRepository.findAll().size()).isEqualTo(1);
    }

}
