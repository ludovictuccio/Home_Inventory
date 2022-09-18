package com.home.inventory.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
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
        sousCategoriesRepository.deleteAllInBatch();
    }

    @AfterEach
    public void setUp() {
        sousCategoriesRepository.findAll().clear();
        sousCategoriesRepository.deleteAllInBatch();
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

        Long idSousCategorieOne = sousCategoriesRepository.findAll().get(0)
                .getId();
        Long idSousCategorieTwo = sousCategoriesRepository.findAll().get(1)
                .getId();
        // THEN
        assertThat(sousCategoriesRepository.findById(idSousCategorieOne).get())
                .isNotNull();

        assertThat(sousCategoriesRepository.findById(idSousCategorieOne).get()
                .getDescription()).isEqualTo("Chambre");
        assertThat(sousCategoriesRepository.findById(idSousCategorieTwo).get())
                .isNotNull();

        assertThat(sousCategoriesRepository.findById(idSousCategorieTwo).get()
                .getDescription()).isEqualTo("Terasse");
    }

}
