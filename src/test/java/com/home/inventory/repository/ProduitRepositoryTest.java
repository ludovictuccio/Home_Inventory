package com.home.inventory.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import com.home.inventory.entities.Categories;
import com.home.inventory.entities.Fournisseur;
import com.home.inventory.entities.Produit;
import com.home.inventory.entities.SousCategories;

@SpringBootTest
@TestPropertySource(locations = "classpath:application.properties")
public class ProduitRepositoryTest {

    @Autowired
    private ProduitRepository produitRepository;

    private static Produit produitOne = new Produit(new Categories("Maison"),
            new SousCategories("Chambre"), new Fournisseur("Casto"),
            "Papier peint rose", LocalDate.of(2020, 1, 8), "Paris", "F005X",
            2.0, 0.0, 25.0, "");

    private static Produit produitTwo = new Produit(new Categories("Maison"),
            new SousCategories("Cuisine"), new Fournisseur("Leroy"), "Evier",
            LocalDate.of(2018, 6, 6), "Niort", "0999984f", 1.0, 0.0, 20.0,
            "Acheté en 2 fois");

    @BeforeEach
    public void setUpPerTest() {
        produitRepository.findAll().clear();
    }

    @Test
    @Tag("FindAll")
    @DisplayName("FindAll - Size OK")
    public void givenTwoProductsSavedInDb_whenFindAll_thenReturnCorrectSize() {
        // GIVEN
        produitRepository.save(produitOne);
        produitRepository.save(produitTwo);
        // WHEN
        List<Produit> produits = produitRepository.findAll();

        // THEN
        assertThat(produits.size()).isEqualTo(2);
    }

    @Test
    @Tag("Save")
    @DisplayName("Products saved - OK")
    public void givenTwoProductsSavedInDb_whenSave_thenReturnProductsSavede() {
        // GIVEN
        // WHEN
        produitRepository.save(produitOne);
        produitRepository.save(produitTwo);

        // THEN
        assertThat(produitRepository.findById(1L).get()).isNotNull();
//        assertThat(produitRepository.findById(1L).get().getCategorieProduit())
//                .isEqualTo("Maison");
//        assertThat(
//                produitRepository.findById(1L).get().getSousCategorieProduit())
//                        .isEqualTo("Chambre");
//        assertThat(produitRepository.findById(1L).get().getFournisseurProduit())
//                .isEqualTo("Casto");
        assertThat(produitRepository.findById(1L).get().getDescription())
                .isEqualTo("Papier peint rose");
        assertThat(produitRepository.findById(1L).get().getDateAchat())
                .isEqualTo(LocalDate.of(2020, 1, 8));
        assertThat(produitRepository.findById(1L).get().getLieuAchat())
                .isEqualTo("Paris");
        assertThat(produitRepository.findById(1L).get().getNoFacture())
                .isEqualTo("F005X");
        assertThat(produitRepository.findById(1L).get().getQuantite())
                .isEqualTo(2.0);
        assertThat(
                produitRepository.findById(1L).get().getPourcentageDeRemise())
                        .isEqualTo(0.0);
        assertThat(
                produitRepository.findById(1L).get().getPrixAchatUnitaireTTC())
                        .isEqualTo(25.0);
        assertThat(produitRepository.findById(1L).get().getCommentaire())
                .isEqualTo("");

        assertThat(produitRepository.findById(2L).get()).isNotNull();
//        assertThat(produitRepository.findById(2L).get().getCategorieProduit())
//                .isEqualTo("Maison");
//        assertThat(
//                produitRepository.findById(2L).get().getSousCategorieProduit())
//                        .isEqualTo("Cuisine");
//        assertThat(produitRepository.findById(2L).get().getFournisseurProduit())
//                .isEqualTo("Leroy");
        assertThat(produitRepository.findById(2L).get().getDescription())
                .isEqualTo("Evier");
        assertThat(produitRepository.findById(2L).get().getDateAchat())
                .isEqualTo(LocalDate.of(2018, 6, 6));
        assertThat(produitRepository.findById(2L).get().getLieuAchat())
                .isEqualTo("Niort");
        assertThat(produitRepository.findById(2L).get().getNoFacture())
                .isEqualTo("0999984f");
        assertThat(produitRepository.findById(2L).get().getQuantite())
                .isEqualTo(1.0);
        assertThat(
                produitRepository.findById(2L).get().getPourcentageDeRemise())
                        .isEqualTo(0.0);
        assertThat(
                produitRepository.findById(2L).get().getPrixAchatUnitaireTTC())
                        .isEqualTo(20.0);
        assertThat(produitRepository.findById(2L).get().getCommentaire())
                .isEqualTo("Acheté en 2 fois");
    }

    @Test
    @Tag("findProduitById")
    @DisplayName("findProduitById - OK")
    public void givenTwoProductsSavedInDb_whenFindProduitById_thenReturnCorrectValues() {
        // GIVEN
        // WHEN
        produitRepository.save(produitOne);
        produitRepository.save(produitTwo);

        // THEN
        assertThat(produitRepository.findProduitById(1L)).isNotNull();
        assertThat(produitRepository.findProduitById(2L)).isNotNull();
        assertThat(produitRepository.findProduitById(3L)).isNull();
    }

}
