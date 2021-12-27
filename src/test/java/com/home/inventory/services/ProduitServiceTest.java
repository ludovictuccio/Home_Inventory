package com.home.inventory.services;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import com.home.inventory.entities.Categories;
import com.home.inventory.entities.Fournisseur;
import com.home.inventory.entities.Produit;
import com.home.inventory.entities.SousCategories;
import com.home.inventory.repository.CategoriesRepository;
import com.home.inventory.repository.FournisseurRepository;
import com.home.inventory.repository.ProduitRepository;
import com.home.inventory.repository.SousCategoriesRepository;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
@Sql(scripts = "classpath:dropAndCreate.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:dropAndCreate.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class ProduitServiceTest {

    @Autowired
    public IProduitService produitService;

    @Autowired
    private ProduitRepository produitRepository;

    @Autowired
    private CategoriesRepository categoriesRepository;

    @Autowired
    private SousCategoriesRepository sousCategoriesRepository;

    @Autowired
    private FournisseurRepository fournisseurRepository;

    private static Produit produit;

    @BeforeEach
    public void setUpPerTest() {
        produitRepository.deleteAllInBatch();
        produitRepository.findAll().clear();

        produit = new Produit(categoriesRepository.findAll().get(0),
                sousCategoriesRepository.findAll().get(0),
                fournisseurRepository.findAll().get(0), "Lampadaires",
                LocalDate.of(2020, 10, 10), "Paris", "F005X", 1.0, 0.0, 50.0,
                "Promo");
    }

    @Test
    @Tag("POST")
    @DisplayName("Add new produit - OK")
    public void givenProduit_whenAdd_thenReturnSaved() {
        // GIVEN
        // WHEN
        Produit result = produitService.addProduit(produit);

        // THEN
        assertThat(result).isNotNull();
        // assertThat(result.getId()).isNotNull();
        assertThat(result.getCategorieProduit().getDescription())
                .isEqualTo("Maison");
        assertThat(result.getSousCategorieProduit().getDescription())
                .isEqualTo("Jardin");
        assertThat(result.getFournisseurProduit().getDescription())
                .isEqualTo("Casto");
        assertThat(result.getDescription()).isEqualTo("Lampadaires");
        assertThat(result.getDateAchat()).isEqualTo(LocalDate.of(2020, 10, 10));
        assertThat(result.getLieuAchat()).isEqualTo("Paris");
        assertThat(result.getNoFacture()).isEqualTo("F005X");
        assertThat(result.getQuantite()).isEqualTo(1.0);
        assertThat(result.getPourcentageDeRemise()).isEqualTo(0.0);
        assertThat(result.getPrixAchatUnitaireTTC()).isEqualTo(50.0);
        assertThat(result.getCommentaire()).isEqualTo("Promo");
    }

    @Test
    @Tag("POST")
    @DisplayName("Add new Produit - ERROR - Produit already exists (same no facture)")
    public void givenExistingProduit_whenAddNewCategoryWithSameFacture_thenReturnNull() {
        // GIVEN
        produitService.addProduit(produit);
        assertThat(produitRepository.findAll().size()).isEqualTo(1);

        Produit sameProduct = new Produit(new Categories("Maison"),
                new SousCategories("Jardin"), new Fournisseur("Casto"),
                "Lampadaires", LocalDate.of(2020, 10, 10), "Paris", "F005X",
                1.0, 0.0, 50.0, "Promo");

        // WHEN
        Produit result = produitService.addProduit(sameProduct);

        // THEN
        assertThat(result).isNull();
        assertThat(produitRepository.findAll().size()).isEqualTo(1);
    }

    @Test
    @Tag("POST")
    @DisplayName("Add new Produit - ERROR - Empty description")
    public void givenZeroProduit_whenAddWithEmptyDescription_thenReturnNull() {
        // GIVEN
        Produit produitWithEmptyDesciption = new Produit(
                new Categories("Maison"), new SousCategories("Jardin"),
                new Fournisseur("Casto"), "", LocalDate.of(2020, 10, 10),
                "Paris", "F005X", 1.0, 0.0, 50.0, "Promo");
        // WHEN
        Produit result = produitService.addProduit(produitWithEmptyDesciption);

        // THEN
        assertThat(result).isNull();
        assertThat(produitRepository.findAll().size()).isEqualTo(0);
    }

    @Test
    @Tag("GET")
    @DisplayName("Get by ID - OK")
    public void givenProduit_whenGetById_thenReturnOk() {
        // GIVEN
        produitService.addProduit(produit);

        // WHEN
        Produit result = produitService
                .getProduitById(produitRepository.findAll().get(0).getId());

        // THEN
        assertThat(result).isNotNull();
        assertThat(result.getId()).isNotNull();
        assertThat(result.getCategorieProduit().getDescription())
                .isEqualTo("Maison");
        assertThat(result.getSousCategorieProduit().getDescription())
                .isEqualTo("Jardin");
        assertThat(result.getFournisseurProduit().getDescription())
                .isEqualTo("Casto");
        assertThat(result.getDescription()).isEqualTo("Lampadaires");
        assertThat(result.getDateAchat()).isEqualTo(LocalDate.of(2020, 10, 10));
        assertThat(result.getLieuAchat()).isEqualTo("Paris");
        assertThat(result.getNoFacture()).isEqualTo("F005X");
        assertThat(result.getQuantite()).isEqualTo(1.0);
        assertThat(result.getPourcentageDeRemise()).isEqualTo(0.0);
        assertThat(result.getPrixAchatUnitaireTTC()).isEqualTo(50.0);
        assertThat(result.getCommentaire()).isEqualTo("Promo");
    }

    @Test
    @Tag("PUT")
    @DisplayName("Update by ID - OK")
    public void givenProduit_whenUpdateById_thenReturnOk() {
        // GIVEN
//        List<Produit> allCategoriesForMaison = new ArrayList<>();
//        allCategoriesForMaison.add(produit);
        Categories categories = new Categories(1L, "Maison");

//        List<Produit> allSousCategoriesForJardin = new ArrayList<>();
//        allSousCategoriesForJardin.add(produit);
        SousCategories sousCategories = new SousCategories(1L, "Jardin");

//        List<Produit> allFournisseurForCasto = new ArrayList<>();
//        allFournisseurForCasto.add(produit);
        Fournisseur fournisseur = new Fournisseur(1L, "Casto");

        categoriesRepository.save(categories);
        sousCategoriesRepository.save(sousCategories);
        fournisseurRepository.save(fournisseur);

        Categories categories2 = new Categories(2L, "Voisin");
        SousCategories sousCategories2 = new SousCategories(2L, "Allée");
        Fournisseur fournisseur2 = new Fournisseur(2L, "Leroy");
        categoriesRepository.save(categories2);
        sousCategoriesRepository.save(sousCategories2);
        fournisseurRepository.save(fournisseur2);

        produitService.addProduit(produit);
        assertThat(produitRepository.findAll().get(0).getCategorieProduit()
                .getDescription()).isEqualTo("Maison");
        assertThat(produitRepository.findAll().get(0).getSousCategorieProduit()
                .getDescription()).isEqualTo("Jardin");
        assertThat(produitRepository.findAll().get(0).getFournisseurProduit()
                .getDescription()).isEqualTo("Casto");

        Produit produitToUpdate = new Produit(categories2, sousCategories2,
                fournisseur2, "Lampadaires update", LocalDate.of(2020, 1, 1),
                "Other city", "F005XOTHER", 11.0, 1.0, 50.0, "Promotion");

        Long categoryId = produitRepository.findAll().get(0).getId();

        // WHEN
        boolean isUpdated = produitService.updateProduitById(produitToUpdate,
                categoryId);

        Produit produitUpdated = produitRepository.findAll().get(0);

        // THEN
        assertThat(isUpdated).isTrue();
        assertThat(produitRepository.findAll().size()).isEqualTo(1);
        assertThat(produitUpdated.getId()).isNotNull();
        assertThat(produitUpdated.getCategorieProduit().getDescription())
                .isEqualTo("Voisin");
        assertThat(produitUpdated.getSousCategorieProduit().getDescription())
                .isEqualTo("Allée");
        assertThat(produitUpdated.getFournisseurProduit().getDescription())
                .isEqualTo("Leroy");
        assertThat(produitUpdated.getDescription())
                .isEqualTo("Lampadaires update");
        assertThat(produitUpdated.getDateAchat())
                .isEqualTo(LocalDate.of(2020, 1, 1));
        assertThat(produitUpdated.getLieuAchat()).isEqualTo("Other city");
        assertThat(produitUpdated.getNoFacture()).isEqualTo("F005XOTHER");
        assertThat(produitUpdated.getQuantite()).isEqualTo(11.0);
        assertThat(produitUpdated.getPourcentageDeRemise()).isEqualTo(1.0);
        assertThat(produitUpdated.getPrixAchatUnitaireTTC()).isEqualTo(50.0);
        assertThat(produitUpdated.getCommentaire()).isEqualTo("Promotion");
    }

    @Test
    @Tag("DELETE")
    @DisplayName("Delete by ID - OK")
    public void givenProduit_whenDeleteWithHisId_thenReturnTrue() {
        // GIVEN
        produitService.addProduit(produit);
        assertThat(produitRepository.findAll().size()).isEqualTo(1);

        Long categoryId = produitRepository.findAll().get(0).getId();

        // WHEN
        boolean isDeleted = produitService.deleteProduitById(categoryId);

        // THEN
        assertThat(isDeleted).isTrue();
        assertThat(produitRepository.findAll().size()).isEqualTo(0);
    }

    @Test
    @Tag("DELETE")
    @DisplayName("Delete by ID - Error - Unkow id")
    public void givenProduit_whenDeleteWithUnknowId_thenReturnFalse() {
        // GIVEN
        produitService.addProduit(produit);
        assertThat(produitRepository.findAll().size()).isEqualTo(1);

        // WHEN
        boolean isDeleted = produitService.deleteProduitById(9999L);

        // THEN
        assertThat(isDeleted).isFalse();
        assertThat(produitRepository.findAll().size()).isEqualTo(1);
    }

}
