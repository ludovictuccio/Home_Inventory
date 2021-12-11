package com.home.inventory.services;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import com.home.inventory.entities.Fournisseur;
import com.home.inventory.repository.FournisseurRepository;

@SpringBootTest
@TestPropertySource(locations = "classpath:application.properties")
public class FournisseurTest {

    @Autowired
    public IFournisseurService fournisseurService;

    @Autowired
    private FournisseurRepository fournisseurRepository;

    @BeforeEach
    public void setUpPerTest() {
        fournisseurRepository.deleteAllInBatch();
        fournisseurRepository.findAll().clear();
    }

    @Test
    @Tag("POST")
    @DisplayName("Add new Fournisseur - OK")
    public void givenFournisseur_whenAdd_thenReturnSaved() {
        // GIVEN
        Fournisseur fournisseur = new Fournisseur("Casto");

        // WHEN
        Fournisseur result = fournisseurService.addFournisseur(fournisseur);

        // THEN
        assertThat(result).isNotNull();
        assertThat(result.getId()).isNotNull();
        assertThat(result.getDescription()).isEqualTo("Casto");
    }

    @Test
    @Tag("POST")
    @DisplayName("Add new Fournisseur - ERROR - Fournisseur already exists (same description)")
    public void givenExistingFournisseur_whenAddNewFournisseurWithSameDescription_thenReturnNull() {
        // GIVEN
        fournisseurRepository.save(new Fournisseur("Casto"));
        assertThat(fournisseurRepository.findAll().size()).isEqualTo(1);

        // WHEN
        Fournisseur result = fournisseurService
                .addFournisseur(new Fournisseur("Casto"));

        // THEN
        assertThat(result).isNull();
        assertThat(fournisseurRepository.findAll().size()).isEqualTo(1);
    }

    @Test
    @Tag("POST")
    @DisplayName("Add new Fournisseur - ERROR - Empty description")
    public void givenZeroFournisseur_whenAddWithEmptyDescription_thenReturnNull() {
        // GIVEN
        // WHEN
        Fournisseur result = fournisseurService
                .addFournisseur(new Fournisseur(""));

        // THEN
        assertThat(result).isNull();
        assertThat(fournisseurRepository.findAll().size()).isEqualTo(0);
    }

    @Test
    @Tag("GET")
    @DisplayName("Get by ID - OK ")
    public void givenFournisseur_whenGetById_thenReturnOk() {
        // GIVEN
        fournisseurRepository.save(new Fournisseur("Casto"));

        // WHEN
        Fournisseur result = fournisseurService.getFournisseurById(
                fournisseurRepository.findAll().get(0).getId());

        // THEN
        assertThat(result).isNotNull();
        assertThat(result.getId()).isNotNull();
        assertThat(result.getDescription()).isEqualTo("Casto");
    }

    @Test
    @Tag("PUT")
    @DisplayName("Update by description - OK")
    public void givenFournisseur_whenUpdateByDescription_thenReturnOk() {
        // GIVEN
        fournisseurRepository.save(new Fournisseur("Casto"));

        Fournisseur fournisseurToUpdate = new Fournisseur("CastoS");

        // WHEN
        boolean isUpdated = fournisseurService
                .updateFournisseurByDescription(fournisseurToUpdate, "Casto");

        // THEN
        assertThat(isUpdated).isTrue();
        assertThat(fournisseurRepository.findAll().size()).isEqualTo(1);
        assertThat(fournisseurRepository.findAll().get(0).getDescription())
                .isEqualTo("CastoS");
    }

    @Test
    @Tag("PUT")
    @DisplayName("Update by ID - OK")
    public void givenFournisseur_whenUpdateById_thenReturnOk() {
        // GIVEN
        fournisseurRepository.save(new Fournisseur("Casto"));

        Fournisseur fournisseurToUpdate = new Fournisseur("CastoS");

        Long fournisseurId = fournisseurRepository.findAll().get(0).getId();

        // WHEN
        boolean isUpdated = fournisseurService
                .updateFournisseurById(fournisseurToUpdate, fournisseurId);

        // THEN
        assertThat(isUpdated).isTrue();
        assertThat(fournisseurRepository.findAll().size()).isEqualTo(1);
        assertThat(fournisseurRepository.findAll().get(0).getDescription())
                .isEqualTo("CastoS");
    }

    @Test
    @Tag("DELETE")
    @DisplayName("Delete by ID - OK")
    public void givenFournisseur_whenDeleteWithHisId_thenReturnTrue() {
        // GIVEN
        fournisseurRepository.save(new Fournisseur("Casto"));
        assertThat(fournisseurRepository.findAll().size()).isEqualTo(1);

        Long fournisseurId = fournisseurRepository.findAll().get(0).getId();

        // WHEN
        boolean isDeleted = fournisseurService
                .deleteFournisseurById(fournisseurId);

        // THEN
        assertThat(isDeleted).isTrue();
        assertThat(fournisseurRepository.findAll().size()).isEqualTo(0);
    }

    @Test
    @Tag("DELETE")
    @DisplayName("Delete by ID - Error - Unkow id")
    public void givenFournisseur_whenDeleteWithUnknowId_thenReturnFalse() {
        // GIVEN
        fournisseurRepository.save(new Fournisseur("Casto"));
        assertThat(fournisseurRepository.findAll().size()).isEqualTo(1);

        // WHEN
        boolean isDeleted = fournisseurService.deleteFournisseurById(9999L);

        // THEN
        assertThat(isDeleted).isFalse();
        assertThat(fournisseurRepository.findAll().size()).isEqualTo(1);
    }
}
