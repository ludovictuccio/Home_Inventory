package com.home.inventory.services;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import com.home.inventory.entities.SousCategories;
import com.home.inventory.repository.SousCategoriesRepository;

@SpringBootTest
@TestPropertySource(locations = "classpath:application.properties")
public class SousCategoriesTest {

    @Autowired
    public ISousCategoriesService sousCategorieService;

    @Autowired
    private SousCategoriesRepository sousCategoriesRepository;

    @BeforeEach
    public void setUpPerTest() {
        sousCategoriesRepository.deleteAllInBatch();
        sousCategoriesRepository.findAll().clear();
    }

    @Test
    @Tag("POST")
    @DisplayName("Add new sous-category - OK")
    public void givenSousCategory_whenAdd_thenReturnSaved() {
        // GIVEN
        SousCategories sousCategory = new SousCategories("Chambre");

        // WHEN
        SousCategories result = sousCategorieService
                .addSousCategory(sousCategory);

        // THEN
        assertThat(result).isNotNull();
        assertThat(result.getId()).isNotNull();
        assertThat(result.getDescription()).isEqualTo("Chambre");
    }

    @Test
    @Tag("POST")
    @DisplayName("Add new sous-category - ERROR - SousCategories already exists (same description)")
    public void givenExistingSousCategory_whenAddNewSousCategoryWithSameDescription_thenReturnNull() {
        // GIVEN
        sousCategoriesRepository.save(new SousCategories("Chambre"));
        assertThat(sousCategoriesRepository.findAll().size()).isEqualTo(1);

        // WHEN
        SousCategories result = sousCategorieService
                .addSousCategory(new SousCategories("Chambre"));

        // THEN
        assertThat(result).isNull();
        assertThat(sousCategoriesRepository.findAll().size()).isEqualTo(1);
    }

    @Test
    @Tag("POST")
    @DisplayName("Add new sous-category - ERROR - Empty description")
    public void givenZeroSousCategory_whenAddWithEmptyDescription_thenReturnNull() {
        // GIVEN
        // WHEN
        SousCategories result = sousCategorieService
                .addSousCategory(new SousCategories(""));

        // THEN
        assertThat(result).isNull();
        assertThat(sousCategoriesRepository.findAll().size()).isEqualTo(0);
    }

    @Test
    @Tag("GET")
    @DisplayName("Get by ID - OK ")
    public void givenSousCategory_whenGetById_thenReturnOk() {
        // GIVEN
        sousCategoriesRepository.save(new SousCategories("Chambre"));

        // WHEN
        SousCategories result = sousCategorieService.getSousCategoryById(
                sousCategoriesRepository.findAll().get(0).getId());

        // THEN
        assertThat(result).isNotNull();
        assertThat(result.getId()).isNotNull();
        assertThat(result.getDescription()).isEqualTo("Chambre");
    }

    @Test
    @Tag("PUT")
    @DisplayName("Update by description - OK")
    public void givenSousCategory_whenUpdateByDescription_thenReturnOk() {
        // GIVEN
        sousCategoriesRepository.save(new SousCategories("Chambre"));

        SousCategories sousCategoryToUpdate = new SousCategories("Chambres");

        // WHEN
        boolean isUpdated = sousCategorieService
                .updateSousCategoriesByDescription(sousCategoryToUpdate,
                        "Chambre");

        // THEN
        assertThat(isUpdated).isTrue();
        assertThat(sousCategoriesRepository.findAll().size()).isEqualTo(1);
        assertThat(sousCategoriesRepository.findAll().get(0).getDescription())
                .isEqualTo("Chambres");
    }

    @Test
    @Tag("PUT")
    @DisplayName("Update by ID - OK")
    public void givenSousCategory_whenUpdateById_thenReturnOk() {
        // GIVEN
        sousCategoriesRepository.save(new SousCategories("Chambre"));

        SousCategories sousCategoryToUpdate = new SousCategories("Chambres");

        Long sousCategoryId = sousCategoriesRepository.findAll().get(0).getId();

        // WHEN
        boolean isUpdated = sousCategorieService
                .updateSousCategoryById(sousCategoryToUpdate, sousCategoryId);

        // THEN
        assertThat(isUpdated).isTrue();
        assertThat(sousCategoriesRepository.findAll().size()).isEqualTo(1);
        assertThat(sousCategoriesRepository.findAll().get(0).getDescription())
                .isEqualTo("Chambres");
    }

    @Test
    @Tag("DELETE")
    @DisplayName("Delete by ID - OK")
    public void givenSousCategory_whenDeleteWithHisId_thenReturnTrue() {
        // GIVEN
        sousCategoriesRepository.save(new SousCategories("Chambre"));
        assertThat(sousCategoriesRepository.findAll().size()).isEqualTo(1);

        Long sousCategoryId = sousCategoriesRepository.findAll().get(0).getId();

        // WHEN
        boolean isDeleted = sousCategorieService
                .deleteSousCategoryById(sousCategoryId);

        // THEN
        assertThat(isDeleted).isTrue();
        assertThat(sousCategoriesRepository.findAll().size()).isEqualTo(0);
    }

    @Test
    @Tag("DELETE")
    @DisplayName("Delete by ID - Error - Unkow id")
    public void givenSousCategory_whenDeleteWithUnknowId_thenReturnFalse() {
        // GIVEN
        sousCategoriesRepository.save(new SousCategories("Chambre"));
        assertThat(sousCategoriesRepository.findAll().size()).isEqualTo(1);

        // WHEN
        boolean isDeleted = sousCategorieService.deleteSousCategoryById(9999L);

        // THEN
        assertThat(isDeleted).isFalse();
        assertThat(sousCategoriesRepository.findAll().size()).isEqualTo(1);
    }
}
