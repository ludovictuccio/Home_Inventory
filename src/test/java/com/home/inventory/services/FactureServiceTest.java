package com.home.inventory.services;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import com.home.inventory.entities.Facture;
import com.home.inventory.repository.FactureRepository;
import com.home.inventory.services.interfaces.IFactureService;

@SpringBootTest
@TestPropertySource(locations = "classpath:application.properties")
public class FactureServiceTest {

    @Autowired
    public IFactureService factureService;

    @Autowired
    private FactureRepository factureRepository;

    private static final String FACTURE = "FX005";
    private static final String FACTURE_UPDATE = "A9999";

    @BeforeEach
    public void setUpPerTest() {
        factureRepository.deleteAllInBatch();
        factureRepository.findAll().clear();
    }

    @Test
    @Tag("POST")
    @DisplayName("Add new Facture - OK")
    public void givenFacture_whenAdd_thenReturnSaved() {
        // GIVEN
        Facture facture = new Facture(FACTURE);

        // WHEN
        Facture result = factureService.addFacture(facture);

        // THEN
        assertThat(result).isNotNull();
        assertThat(result.getId()).isNotNull();
        assertThat(result.getDescription()).isEqualTo(FACTURE);
    }

    @Test
    @Tag("POST")
    @DisplayName("Add new Facture - ERROR - Facture already exists (same description)")
    public void givenExistingFacture_whenAddNewCategoryWithSameDescription_thenReturnNull() {
        // GIVEN
        factureRepository.save(new Facture(FACTURE));
        assertThat(factureRepository.findAll().size()).isEqualTo(1);

        // WHEN
        Facture result = factureService.addFacture(new Facture(FACTURE));

        // THEN
        assertThat(result).isNull();
        assertThat(factureRepository.findAll().size()).isEqualTo(1);
    }

    @Test
    @Tag("POST")
    @DisplayName("Add new Facture - ERROR - Empty description")
    public void givenZeroFacture_whenAddWithEmptyDescription_thenReturnNull() {
        // GIVEN
        // WHEN
        Facture result = factureService.addFacture(new Facture(""));

        // THEN
        assertThat(result).isNull();
        assertThat(factureRepository.findAll().size()).isEqualTo(0);
    }

    @Test
    @Tag("GET")
    @DisplayName("Get by ID - OK ")
    public void givenCategoryFacture_whenGetById_thenReturnOk() {
        // GIVEN
        factureRepository.save(new Facture(FACTURE));

        // WHEN
        Facture result = factureService
                .getFactureById(factureRepository.findAll().get(0).getId());

        // THEN
        assertThat(result).isNotNull();
        assertThat(result.getId()).isNotNull();
        assertThat(result.getDescription()).isEqualTo(FACTURE);
    }

    @Test
    @Tag("PUT")
    @DisplayName("Update by description - OK")
    public void givenFacture_whenUpdateByDescription_thenReturnOk() {
        // GIVEN
        factureRepository.save(new Facture(FACTURE));

        Facture factureToUpdate = new Facture(FACTURE_UPDATE);

        // WHEN
        boolean isUpdated = factureService
                .updateFactureByDescription(factureToUpdate, FACTURE);

        // THEN
        assertThat(isUpdated).isTrue();
        assertThat(factureRepository.findAll().size()).isEqualTo(1);
        assertThat(factureRepository.findAll().get(0).getDescription())
                .isEqualTo(FACTURE_UPDATE);
    }

    @Test
    @Tag("PUT")
    @DisplayName("Update by ID - OK")
    public void givenFacture_whenUpdateById_thenReturnOk() {
        // GIVEN
        factureRepository.save(new Facture(FACTURE));

        Facture factureToUpdate = new Facture(FACTURE_UPDATE);

        Long categoryId = factureRepository.findAll().get(0).getId();

        // WHEN
        boolean isUpdated = factureService.updateFactureById(factureToUpdate,
                categoryId);

        // THEN
        assertThat(isUpdated).isTrue();
        assertThat(factureRepository.findAll().size()).isEqualTo(1);
        assertThat(factureRepository.findAll().get(0).getDescription())
                .isEqualTo(FACTURE_UPDATE);
    }

    @Test
    @Tag("DELETE")
    @DisplayName("Delete by ID - OK")
    public void givenFacture_whenDeleteWithHisId_thenReturnTrue() {
        // GIVEN
        factureRepository.save(new Facture(FACTURE));
        assertThat(factureRepository.findAll().size()).isEqualTo(1);

        Long categoryId = factureRepository.findAll().get(0).getId();

        // WHEN
        boolean isDeleted = factureService.deleteFactureById(categoryId);

        // THEN
        assertThat(isDeleted).isTrue();
        assertThat(factureRepository.findAll().size()).isEqualTo(0);
    }

    @Test
    @Tag("DELETE")
    @DisplayName("Delete by ID - Error - Unkow id")
    public void givenFacture_whenDeleteWithUnknowId_thenReturnFalse() {
        // GIVEN
        factureRepository.save(new Facture(FACTURE));
        assertThat(factureRepository.findAll().size()).isEqualTo(1);

        // WHEN
        boolean isDeleted = factureService.deleteFactureById(9999L);

        // THEN
        assertThat(isDeleted).isFalse();
        assertThat(factureRepository.findAll().size()).isEqualTo(1);
    }

}
