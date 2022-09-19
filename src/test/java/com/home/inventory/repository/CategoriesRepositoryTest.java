package com.home.inventory.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import com.home.inventory.entities.Categories;

@SpringBootTest
@TestPropertySource(locations = "classpath:application.properties")
public class CategoriesRepositoryTest {

    @Autowired
    private CategoriesRepository categoriesRepository;

    private static Categories categorieOne = new Categories("Maison");
    private static Categories categorieTwo = new Categories("Jardin");

    @BeforeEach
    public void setUpPerTest() {
        categoriesRepository.deleteAll();
    }

    @Test
    @Tag("FindAll")
    @DisplayName("FindAll - Size OK")
    public void givenTwoCategoriesSavedInDb_whenFindAll_thenReturnCorrectSize() {
        // GIVEN
        categoriesRepository.save(categorieOne);
        categoriesRepository.save(categorieTwo);
        // WHEN
        List<Categories> categories = categoriesRepository.findAll();

        // THEN
        assertThat(categories.size()).isEqualTo(2);
    }

    @Test
    @Tag("Save")
    @DisplayName("Categories saved - OK")
    public void givenTwoCategoriesSavedInDb_whenSave_thenReturnSaved() {
        // GIVEN
        // WHEN
        categoriesRepository.save(categorieOne);
        categoriesRepository.save(categorieTwo);

        // THEN
        Long idCategorieOne = categoriesRepository.findAll().get(0).getId();
        Long idCategorieTwo = categoriesRepository.findAll().get(1).getId();

        assertThat(categoriesRepository.findById(idCategorieOne).get()).isNotNull();
        assertThat(categoriesRepository.findById(idCategorieOne).get().getDescription())
                .isEqualTo("Maison");

        assertThat(categoriesRepository.findById(idCategorieTwo).get()).isNotNull();
        assertThat(categoriesRepository.findById(idCategorieTwo).get().getDescription())
                .isEqualTo("Jardin");
    }

    @Test
    @Tag("findCategoriesById")
    @DisplayName("findCategoriesById - OK")
    public void givenTwoCategoriesSavedInDb_whenFindCategoriesById_thenReturnCorrectValues() {
        // GIVEN
        // WHEN
        categoriesRepository.save(categorieOne);
        categoriesRepository.save(categorieTwo);

        // THEN
        Long idCategorieOne = categoriesRepository.findAll().get(0).getId();
        Long idCategorieTwo = categoriesRepository.findAll().get(1).getId();

        assertThat(categoriesRepository.findCategoriesById(idCategorieOne)).isNotNull();
        assertThat(categoriesRepository.findCategoriesById(idCategorieTwo)).isNotNull();
        assertThat(categoriesRepository.findCategoriesById(3L)).isNull();
    }

    @Test
    @Tag("findCategoriesByDescription")
    @DisplayName("findCategoriesByDescription - OK")
    public void givenTwoCategoriesSavedInDb_whenFindCategoriesByDescription_thenReturnCorrectValues() {
        // GIVEN
        // WHEN
        categoriesRepository.save(categorieOne);
        categoriesRepository.save(categorieTwo);

        // THEN
        assertThat(categoriesRepository.findCategoriesByDescription("Maison"))
                .isNotNull();
        assertThat(categoriesRepository.findCategoriesByDescription("Jardin"))
                .isNotNull();
        assertThat(categoriesRepository.findCategoriesByDescription("Other"))
                .isNull();
    }

}
