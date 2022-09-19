package com.home.inventory.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import com.home.inventory.entities.Categories;
import com.home.inventory.entities.Facture;
import com.home.inventory.entities.Fournisseur;
import com.home.inventory.entities.Produit;
import com.home.inventory.entities.SousCategories;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
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

    @BeforeAll
    public void setUp() {
        produitRepository.deleteAll();

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
        produitRepository.save(produitOne);
        produitRepository.save(produitTwo);
    }

    @Test
    @Tag("FindAll")
    @DisplayName("FindAll - Size OK")
    public void givenTwoProductsSavedInDb_whenFindAll_thenReturnCorrectSize() {
        // GIVEN
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

        Long idProduitOne = produitRepository.findAll().get(0).getId();
        Long idProduitTwo = produitRepository.findAll().get(1).getId();

        // THEN
        assertThat(produitRepository.findById(idProduitOne).get()).isNotNull();
        assertThat(produitRepository.findById(idProduitOne).get()
                .getCategorieProduit().getDescription()).isEqualTo("Maison");
        assertThat(produitRepository.findById(idProduitOne).get()
                .getSousCategorieProduit().getDescription())
                        .isEqualTo("Chambre");
        assertThat(produitRepository.findById(idProduitOne).get()
                .getFournisseurProduit().getDescription()).isEqualTo("Casto");
        assertThat(produitRepository.findById(idProduitOne).get()
                .getFactureProduit().getDescription()).isEqualTo("A0001");
        assertThat(
                produitRepository.findById(idProduitOne).get().getDescription())
                        .isEqualTo("Papier peint rose");
        assertThat(
                produitRepository.findById(idProduitOne).get().getDateAchat())
                        .isEqualTo(LocalDate.of(2020, 1, 8));
        assertThat(
                produitRepository.findById(idProduitOne).get().getLieuAchat())
                        .isEqualTo("Paris");
        assertThat(produitRepository.findById(idProduitOne).get().getQuantite())
                .isEqualTo(2.0);
        assertThat(produitRepository.findById(idProduitOne).get()
                .getPourcentageDeRemise()).isEqualTo(0.0);
        assertThat(produitRepository.findById(idProduitOne).get()
                .getPrixAchatUnitaireTTC()).isEqualTo(25.0);
        assertThat(
                produitRepository.findById(idProduitOne).get().getCommentaire())
                        .isEqualTo("");

        assertThat(produitRepository.findById(idProduitTwo).get()).isNotNull();
        assertThat(produitRepository.findById(idProduitTwo).get()
                .getCategorieProduit().getDescription()).isEqualTo("Maison");
        assertThat(produitRepository.findById(idProduitTwo).get()
                .getSousCategorieProduit().getDescription())
                        .isEqualTo("Cuisine");
        assertThat(produitRepository.findById(idProduitTwo).get()
                .getFournisseurProduit().getDescription()).isEqualTo("Leroy");
        assertThat(produitRepository.findById(idProduitTwo).get()
                .getFactureProduit().getDescription()).isEqualTo("A0002");
        assertThat(
                produitRepository.findById(idProduitTwo).get().getDescription())
                        .isEqualTo("Evier");
        assertThat(
                produitRepository.findById(idProduitTwo).get().getDateAchat())
                        .isEqualTo(LocalDate.of(2018, 6, 6));
        assertThat(
                produitRepository.findById(idProduitTwo).get().getLieuAchat())
                        .isEqualTo("Niort");
        assertThat(produitRepository.findById(idProduitTwo).get().getQuantite())
                .isEqualTo(1.0);
        assertThat(produitRepository.findById(idProduitTwo).get()
                .getPourcentageDeRemise()).isEqualTo(0.0);
        assertThat(produitRepository.findById(idProduitTwo).get()
                .getPrixAchatUnitaireTTC()).isEqualTo(20.0);
        assertThat(
                produitRepository.findById(idProduitTwo).get().getCommentaire())
                        .isEqualTo("Acheté en 2 fois");
    }

    @Test
    @Tag("findProduitById")
    @DisplayName("findProduitById - OK")
    public void givenTwoProductsSavedInDb_whenFindProduitById_thenReturnCorrectValues() {
        // GIVEN
        // WHEN

        // THEN
        assertThat(produitRepository.findAll().size()).isEqualTo(2);
        assertThat(produitRepository.findAll().get(0).getId()).isNotNull();
        assertThat(produitRepository.findAll().get(1).getId()).isNotNull();
    }

    @Test
    @Tag("findProduitByFactureProduit")
    @DisplayName("findProduitByFactureProduit - OK")
    public void givenTwoProductsSavedInDb_whenFindProduitByNoFacture_thenReturnCorrectValues() {
        // GIVEN
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
    @Tag("findProduitByFactureProduit")
    @DisplayName("findProduitByFactureProduit - Error - Unknow")
    public void givenTwoProductsSavedInDb_whenFindProduitByUnknoNoFacture_thenReturnCorrectValues() {
        // GIVEN
        // WHEN
        Produit result = produitRepository.findProduitByFactureProduit(null);

        // THEN
        assertThat(result).isNull();
    }

}
