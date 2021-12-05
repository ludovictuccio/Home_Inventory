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

import com.home.inventory.entities.Fournisseur;

@SpringBootTest
@TestPropertySource(locations = "classpath:application.properties")
public class FournisseurRepositoryTest {

    @Autowired
    private FournisseurRepository fournisseurRepository;

    private static Fournisseur fournisseurOne = new Fournisseur("Leroy");

    private static Fournisseur fournisseurTwo = new Fournisseur("Casto");

    @BeforeEach
    public void setUpPerTest() {
        fournisseurRepository.findAll().clear();
    }

    @Test
    @Tag("FindAll")
    @DisplayName("FindAll - Size OK")
    public void givenTwoFournisseursSavedInDb_whenFindAll_thenReturnCorrectSize() {
        // GIVEN
        fournisseurRepository.save(fournisseurOne);
        fournisseurRepository.save(fournisseurTwo);
        // WHEN
        List<Fournisseur> fournisseurs = fournisseurRepository.findAll();

        // THEN
        assertThat(fournisseurs.size()).isEqualTo(2);
    }

    @Test
    @Tag("Save")
    @DisplayName("Fournisseurs saved - OK")
    public void givenTwoFournisseurSavedInDb_whenSave_thenReturnSaved() {
        // GIVEN
        // WHEN
        fournisseurRepository.save(fournisseurOne);
        fournisseurRepository.save(fournisseurTwo);

        // THEN
        assertThat(fournisseurRepository.findById(1L).get()).isNotNull();

        assertThat(fournisseurRepository.findById(1L).get().getDescription())
                .isEqualTo("Leroy");
        assertThat(fournisseurRepository.findById(2L).get()).isNotNull();

        assertThat(fournisseurRepository.findById(2L).get().getDescription())
                .isEqualTo("Casto");
    }

    @Test
    @Tag("findFournisseurById")
    @DisplayName("findFournisseurById - OK")
    public void givenTwoFournisseursSavedInDb_whenFindFournisseursById_thenReturnCorrectValues() {
        // GIVEN
        // WHEN
        fournisseurRepository.save(fournisseurOne);
        fournisseurRepository.save(fournisseurTwo);

        // THEN
        assertThat(fournisseurRepository.findFournisseurById(1L)).isNotNull();
        assertThat(fournisseurRepository.findFournisseurById(2L)).isNotNull();
        assertThat(fournisseurRepository.findFournisseurById(3L)).isNull();
    }

}
