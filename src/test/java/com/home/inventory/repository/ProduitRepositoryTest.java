package com.home.inventory.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import com.home.inventory.entities.Categories;
import com.home.inventory.entities.Facture;
import com.home.inventory.entities.Fournisseur;
import com.home.inventory.entities.Produit;
import com.home.inventory.entities.SousCategories;

@SpringBootTest
@TestPropertySource(locations = "classpath:application.properties")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProduitRepositoryTest {

    @Autowired
    private ProduitRepository produitRepository;

    @Autowired
    private CategoriesRepository categoriesRepository;

    @Autowired
    private SousCategoriesRepository sousCategoriesRepository;

    @Autowired
    private FournisseurRepository fournisseurRepository;

    @Autowired
    private FactureRepository factureRepository;

    private static Categories categoriesOne;
    private static SousCategories sousCategoriesOne;
    private static SousCategories sousCategoriesTwo;
    private static Fournisseur fournisseurOne;
    private static Fournisseur fournisseurTwo;
    private static Facture factureOne;
    private static Facture factureTwo;
    private static Produit produitOne;
    private static Produit produitTwo;

    @BeforeEach
    public void setUpPerTest() {
        produitRepository.deleteAllInBatch();
        produitRepository.findAll().clear();

        categoriesOne = new Categories(1L, "Maison");
        categoriesRepository.save(categoriesOne);

        sousCategoriesOne = new SousCategories(1L, "Chambre");
        sousCategoriesTwo = new SousCategories(2L, "Cuisine");
        sousCategoriesRepository.save(sousCategoriesOne);
        sousCategoriesRepository.save(sousCategoriesTwo);

        fournisseurOne = new Fournisseur(1L, "Casto");
        fournisseurTwo = new Fournisseur(2L, "Leroy");
        fournisseurRepository.save(fournisseurOne);
        fournisseurRepository.save(fournisseurTwo);

        factureOne = new Facture(1L, "A0001");
        factureTwo = new Facture(2L, "A0002");
        factureRepository.save(factureOne);
        factureRepository.save(factureTwo);

        produitOne = new Produit(categoriesOne, sousCategoriesOne,
                fournisseurOne, factureOne, "Papier peint rose",
                LocalDate.of(2020, 1, 8), "Paris", 2.0, 0.0, 25.0, "");
        produitTwo = new Produit(categoriesOne, sousCategoriesTwo,
                fournisseurTwo, factureTwo, "Evier", LocalDate.of(2018, 6, 6),
                "Niort", 1.0, 0.0, 20.0, "Acheté en 2 fois");
    }

    @Test
    @Order(5)
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
    @Order(1)
    @Tag("Save")
    @DisplayName("Products saved - OK")
    public void givenTwoProductsSavedInDb_whenSave_thenReturnProductsSavede() {
        // GIVEN
        // WHEN
        produitRepository.save(produitOne);
        produitRepository.save(produitTwo);

        // THEN
        assertThat(produitRepository.findById(1L).get()).isNotNull();
        assertThat(produitRepository.findById(1L).get().getCategorieProduit()
                .getDescription()).isEqualTo("Maison");
        assertThat(produitRepository.findById(1L).get()
                .getSousCategorieProduit().getDescription())
                        .isEqualTo("Chambre");
        assertThat(produitRepository.findById(1L).get().getFournisseurProduit()
                .getDescription()).isEqualTo("Casto");
        assertThat(produitRepository.findById(1L).get().getFactureProduit()
                .getDescription()).isEqualTo("A0001");
        assertThat(produitRepository.findById(1L).get().getDescription())
                .isEqualTo("Papier peint rose");
        assertThat(produitRepository.findById(1L).get().getDateAchat())
                .isEqualTo(LocalDate.of(2020, 1, 8));
        assertThat(produitRepository.findById(1L).get().getLieuAchat())
                .isEqualTo("Paris");
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
        assertThat(produitRepository.findById(2L).get().getCategorieProduit()
                .getDescription()).isEqualTo("Maison");
        assertThat(produitRepository.findById(2L).get()
                .getSousCategorieProduit().getDescription())
                        .isEqualTo("Cuisine");
        assertThat(produitRepository.findById(2L).get().getFournisseurProduit()
                .getDescription()).isEqualTo("Leroy");
        assertThat(produitRepository.findById(2L).get().getFactureProduit()
                .getDescription()).isEqualTo("A0002");
        assertThat(produitRepository.findById(2L).get().getDescription())
                .isEqualTo("Evier");
        assertThat(produitRepository.findById(2L).get().getDateAchat())
                .isEqualTo(LocalDate.of(2018, 6, 6));
        assertThat(produitRepository.findById(2L).get().getLieuAchat())
                .isEqualTo("Niort");
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
    @Order(2)
    @Tag("findProduitById")
    @DisplayName("findProduitById - OK")
    public void givenTwoProductsSavedInDb_whenFindProduitById_thenReturnCorrectValues() {
        // GIVEN
        // WHEN
        produitRepository.save(produitOne);
        produitRepository.save(produitTwo);

        // THEN
        assertThat(produitRepository.findAll().size()).isEqualTo(2);
        assertThat(produitRepository.findAll().get(0).getId()).isNotNull();
        assertThat(produitRepository.findAll().get(1).getId()).isNotNull();
    }

    @Test
    @Order(3)
    @Tag("findProduitByFactureProduit")
    @DisplayName("findProduitByFactureProduit - OK")
    public void givenTwoProductsSavedInDb_whenFindProduitByNoFacture_thenReturnCorrectValues() {
        // GIVEN
        produitRepository.save(produitOne);
        produitRepository.save(produitTwo);
        // WHEN
        Produit result = produitRepository
                .findProduitByFactureProduit(factureOne);
        Produit result2 = produitRepository
                .findProduitByFactureProduit(factureTwo);

        // THEN
        assertThat(result).isNotNull();
        assertThat(result.getDescription()).isEqualTo("Papier peint rose");
        assertThat(result.getLieuAchat()).isEqualTo("Paris");
        assertThat(result.getQuantite()).isEqualTo(2L);

        assertThat(result2).isNotNull();
        assertThat(result2.getDescription()).isEqualTo("Evier");
        assertThat(result2.getLieuAchat()).isEqualTo("Niort");
        assertThat(result2.getQuantite()).isEqualTo(1L);
    }

    @Test
    @Order(4)
    @Tag("findProduitByFactureProduit")
    @DisplayName("findProduitByFactureProduit - Error - Unknow")
    public void givenTwoProductsSavedInDb_whenFindProduitByUnknoNoFacture_thenReturnCorrectValues() {
        // GIVEN
        produitRepository.save(produitOne);
        produitRepository.save(produitTwo);
        // WHEN
        Produit result = produitRepository.findProduitByFactureProduit(null);

        // THEN
        assertThat(result).isNull();
    }

}
