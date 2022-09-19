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

import com.home.inventory.entities.Facture;

@SpringBootTest
@TestPropertySource(locations = "classpath:application.properties")
public class FactureRepositoryTest {

    @Autowired
    private FactureRepository factureRepository;

    @Autowired
    private ProduitRepository produitRepository;

    private static final String FACTURE_ONE = "A0001";
    private static final String FACTURE_TWO = "B0007";

    private static Facture factureOne = new Facture(FACTURE_ONE);
    private static Facture factureTwo = new Facture(FACTURE_TWO);

    @BeforeEach
    public void setUpPerTest() {
        factureRepository.findAll().clear();
        factureRepository.deleteAllInBatch();
        factureRepository.deleteAll();
        produitRepository.findAll().clear();
        produitRepository.deleteAllInBatch();
    }

    @AfterEach
    public void setUp() {
        factureRepository.findAll().clear();
        factureRepository.deleteAllInBatch();
        factureRepository.deleteAll();
        produitRepository.findAll().clear();
        produitRepository.deleteAllInBatch();
    }

    @Test
    @Tag("FindAll")
    @DisplayName("FindAll - Size OK")
    public void givenTwoFacturesSavedInDb_whenFindAll_thenReturnCorrectSize() {
        // GIVEN
        factureRepository.save(factureOne);
        factureRepository.save(factureTwo);
        // WHEN
        List<Facture> result = factureRepository.findAll();

        // THEN
        assertThat(result.size()).isEqualTo(2);
    }

    @Test
    @Tag("Save")
    @DisplayName("Facture saved - OK")
    public void givenTwoFactureSavedInDb_whenSave_thenReturnSaved() {
        // GIVEN
        // WHEN
        factureRepository.save(factureOne);
        factureRepository.save(factureTwo);

        // THEN
        assertThat(factureRepository.findAll().size()).isEqualTo(2);
        assertThat(factureRepository.findAll().get(0).getId()).isNotNull();
        assertThat(factureRepository.findAll().get(0).getDescription())
                .isEqualTo(FACTURE_ONE);
        assertThat(factureRepository.findAll().get(1).getId()).isNotNull();
        assertThat(factureRepository.findAll().get(1).getDescription())
                .isEqualTo(FACTURE_TWO);
    }

    @Test
    @Tag("findFactureById")
    @DisplayName("findFactureById - OK")
    public void givenTwoFactureSavedInDb_whenFindFactureById_thenReturnCorrectValues() {
        // GIVEN
        // WHEN
        factureRepository.save(factureOne);
        factureRepository.save(factureTwo);

        // THEN
        assertThat(factureRepository.findAll().size()).isEqualTo(2);
        assertThat(factureRepository.findAll().get(0).getId()).isNotNull();
        assertThat(factureRepository.findAll().get(1).getId()).isNotNull();

    }

    @Test
    @Tag("findFactureByDescription")
    @DisplayName("findFactureByDescription - OK")
    public void givenTwoFactureSavedInDb_whenFindFactureByDescription_thenReturnCorrectValues() {
        // GIVEN
        // WHEN
        factureRepository.save(factureOne);
        factureRepository.save(factureTwo);

        // THEN
        assertThat(factureRepository.findFactureByDescription(FACTURE_ONE))
                .isNotNull();
        assertThat(factureRepository.findFactureByDescription(FACTURE_TWO))
                .isNotNull();
        assertThat(factureRepository.findFactureByDescription("Other"))
                .isNull();
    }

}
