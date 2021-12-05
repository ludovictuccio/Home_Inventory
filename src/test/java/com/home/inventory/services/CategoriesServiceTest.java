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
        assertThat(result.getId()).isEqualTo(1L);
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

}
