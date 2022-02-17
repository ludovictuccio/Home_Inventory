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
import com.home.inventory.entities.Facture;
import com.home.inventory.entities.Fournisseur;
import com.home.inventory.entities.Produit;
import com.home.inventory.entities.SousCategories;
import com.home.inventory.repository.CategoriesRepository;
import com.home.inventory.repository.FactureRepository;
import com.home.inventory.repository.FournisseurRepository;
import com.home.inventory.repository.ProduitRepository;
import com.home.inventory.repository.SousCategoriesRepository;
import com.home.inventory.services.interfaces.IProduitService;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
@Sql(scripts = "classpath:dropAndCreate.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@Sql(scripts = {
        "classpath:dbTest.sql" }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
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

    @Autowired
    private FactureRepository factureRepository;

    private static Produit produit;

    private static final String CATEGORIE_ONE = "Maison";
    private static final String SOUS_CATEGORIE_ONE = "Chambre";
    private static final String FOURNISSEUR = "Castorama";
    private static final String FACTURE = "A0001";

    @BeforeEach
    public void setUpPerTest() {
        produitRepository.deleteAllInBatch();
        produitRepository.findAll().clear();

        produit = new Produit(categoriesRepository.findAll().get(0),
                sousCategoriesRepository.findAll().get(0),
                fournisseurRepository.findAll().get(0),
                factureRepository.findAll().get(0), "Lampadaires",
                LocalDate.of(2020, 10, 10), "Paris", 1.0, 0.0, 50.0, "Promo");
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
        assertThat(result.getId()).isNotNull();
        assertThat(result.getCategorieProduit().getDescription())
                .isEqualTo(CATEGORIE_ONE);
        assertThat(result.getSousCategorieProduit().getDescription())
                .isEqualTo(SOUS_CATEGORIE_ONE);
        assertThat(result.getFournisseurProduit().getDescription())
                .isEqualTo(FOURNISSEUR);
        assertThat(result.getFactureProduit().getDescription())
                .isEqualTo(FACTURE);
        assertThat(result.getDescription()).isEqualTo("Lampadaires");
        assertThat(result.getDateAchat()).isEqualTo(LocalDate.of(2020, 10, 10));
        assertThat(result.getLieuAchat()).isEqualTo("Paris");
        assertThat(result.getQuantite()).isEqualTo(1.0);
        assertThat(result.getPourcentageDeRemise()).isEqualTo(0.0);
        assertThat(result.getPrixAchatUnitaireTTC()).isEqualTo(50.0);
        assertThat(result.getCommentaire()).isEqualTo("Promo");
    }

    @Test
    @Tag("POST")
    @DisplayName("Add new Produit - ERROR - Empty description")
    public void givenZeroProduit_whenAddWithEmptyDescription_thenReturnNull() {
        // GIVEN
        Produit produitWithEmptyDesciption = new Produit(
                categoriesRepository.findAll().get(0),
                sousCategoriesRepository.findAll().get(0),
                fournisseurRepository.findAll().get(0),
                factureRepository.findAll().get(0), "",
                LocalDate.of(2020, 10, 10), "", 1.0, 0.0, 50.0, "Promo");
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
                .isEqualTo(CATEGORIE_ONE);
        assertThat(result.getSousCategorieProduit().getDescription())
                .isEqualTo(SOUS_CATEGORIE_ONE);
        assertThat(result.getFournisseurProduit().getDescription())
                .isEqualTo(FOURNISSEUR);
        assertThat(result.getFactureProduit().getDescription())
                .isEqualTo(FACTURE);
        assertThat(result.getDescription()).isEqualTo("Lampadaires");
        assertThat(result.getDateAchat()).isEqualTo(LocalDate.of(2020, 10, 10));
        assertThat(result.getLieuAchat()).isEqualTo("Paris");
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
        Categories categories2 = new Categories(2L, "Voisin");
        SousCategories sousCategories2 = new SousCategories(2L, "Allée");
        Fournisseur fournisseur2 = new Fournisseur(2L, "Leroy");
        Facture facture2 = new Facture(2L, "A0006");
        categoriesRepository.save(categories2);
        sousCategoriesRepository.save(sousCategories2);
        fournisseurRepository.save(fournisseur2);
        factureRepository.save(facture2);

        produitService.addProduit(produit);
        assertThat(produitRepository.findAll().get(0).getCategorieProduit()
                .getDescription()).isEqualTo(CATEGORIE_ONE);
        assertThat(produitRepository.findAll().get(0).getSousCategorieProduit()
                .getDescription()).isEqualTo(SOUS_CATEGORIE_ONE);
        assertThat(produitRepository.findAll().get(0).getFournisseurProduit()
                .getDescription()).isEqualTo(FOURNISSEUR);
        assertThat(produitRepository.findAll().get(0).getFactureProduit()
                .getDescription()).isEqualTo(FACTURE);

        Produit produitToUpdate = new Produit(categories2, sousCategories2,
                fournisseur2, facture2, "Lampadaires update",
                LocalDate.of(2020, 1, 1), "Other city", 11.0, 1.0, 50.0,
                "Promotion");

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
        assertThat(produitUpdated.getFactureProduit().getDescription())
                .isEqualTo("A0006");
        assertThat(produitUpdated.getDescription())
                .isEqualTo("Lampadaires update");
        assertThat(produitUpdated.getDateAchat())
                .isEqualTo(LocalDate.of(2020, 1, 1));
        assertThat(produitUpdated.getLieuAchat()).isEqualTo("Other city");
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
