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
        fournisseurRepository.deleteAllInBatch();
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

        Long idFournisseurOne = fournisseurRepository.findAll().get(0).getId();
        Long idFournisseurTwo = fournisseurRepository.findAll().get(1).getId();

        // THEN
        assertThat(fournisseurRepository.findAll().size()).isEqualTo(2);
        assertThat(fournisseurRepository.findById(idFournisseurOne).get())
                .isNotNull();
        assertThat(fournisseurRepository.findById(idFournisseurOne).get()
                .getDescription()).isEqualTo("Leroy");
        assertThat(fournisseurRepository.findById(idFournisseurTwo).get())
                .isNotNull();
        assertThat(fournisseurRepository.findById(idFournisseurTwo).get()
                .getDescription()).isEqualTo("Casto");
    }

}
