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

import com.home.inventory.entities.SousCategories;

@SpringBootTest
@TestPropertySource(locations = "classpath:application.properties")
public class SousCategoriesRepositoryTest {

    @Autowired
    private SousCategoriesRepository sousCategoriesRepository;

    private static SousCategories sousCategorieOne = new SousCategories(
            "Chambre");

    private static SousCategories sousCategorieTwo = new SousCategories(
            "Terasse");

    @BeforeEach
    public void setUpPerTest() {
        sousCategoriesRepository.findAll().clear();
    }

    @Test
    @Tag("FindAll")
    @DisplayName("FindAll - Size OK")
    public void givenTwoSousCategoriesSavedInDb_whenFindAll_thenReturnCorrectSize() {
        // GIVEN
        sousCategoriesRepository.save(sousCategorieOne);
        sousCategoriesRepository.save(sousCategorieTwo);
        // WHEN
        List<SousCategories> souscCategories = sousCategoriesRepository
                .findAll();

        // THEN
        assertThat(souscCategories.size()).isEqualTo(2);
    }

    @Test
    @Tag("Save")
    @DisplayName("Categories saved - OK")
    public void givenTwoSousCategoriesSavedInDb_whenSave_thenReturnSaved() {
        // GIVEN
        // WHEN
        sousCategoriesRepository.save(sousCategorieOne);
        sousCategoriesRepository.save(sousCategorieTwo);

        // THEN
        assertThat(sousCategoriesRepository.findById(1L).get()).isNotNull();

        assertThat(sousCategoriesRepository.findById(1L).get().getDescription())
                .isEqualTo("Chambre");
        assertThat(sousCategoriesRepository.findById(2L).get()).isNotNull();

        assertThat(sousCategoriesRepository.findById(2L).get().getDescription())
                .isEqualTo("Terasse");
    }

    @Test
    @Tag("findSousCategoriesById")
    @DisplayName("findSousCategoriesById - OK")
    public void givenTwoSousCategoriesSavedInDb_whenFindSousCategoriesById_thenReturnCorrectValues() {
        // GIVEN
        // WHEN
        sousCategoriesRepository.save(sousCategorieOne);
        sousCategoriesRepository.save(sousCategorieTwo);

        // THEN
        assertThat(sousCategoriesRepository.findSousCategoriesById(1L))
                .isNotNull();
        assertThat(sousCategoriesRepository.findSousCategoriesById(2L))
                .isNotNull();
        assertThat(sousCategoriesRepository.findSousCategoriesById(3L))
                .isNull();
    }

}
