package com.home.inventory.services;

import com.home.inventory.entities.Categories;
import com.home.inventory.repository.CategoriesRepository;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.home.inventory.entities.Produit;
import com.home.inventory.repository.ProduitRepository;
import com.home.inventory.services.interfaces.IProduitService;
import com.home.inventory.util.ConstraintsValidator;
import com.home.inventory.util.PriceCalculator;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProduitService implements IProduitService {

    private static final Logger LOGGER = LoggerFactory
            .getLogger("ProduitService");

    @Autowired
    private ProduitRepository produitRepository;

    @Autowired
    private CategoriesRepository categoriesRepository;

    /**
     * {@inheritDoc}
     */
    @Transactional
    public Produit addProduit(final Produit produit) {

        if (ConstraintsValidator.checkValidProduit(produit) == null) {
            return null;
        }
        // Calcul de la remise
        Double prixFinalAvecRemise = PriceCalculator
                .calculateFinalPriceWithDiscount(
                        produit.getPrixAchatUnitaireTTC(),
                        produit.getQuantite(),
                        produit.getPourcentageDeRemise());
        produit.setPrixFinalAvecRemise(prixFinalAvecRemise);
        produitRepository.saveAndFlush(produit);
        LOGGER.debug("Succes new produit creation");
        return produit;
    }

    /**
     * {@inheritDoc}
     */
    public Produit getProduitById(final Long id) {
        return produitRepository.findProduitById(id);
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    public boolean updateProduitById(final Produit produitToUpdate) {

        // Si la catégorie ou la sous-categorie ou facture ou fournisseur a été changé (objet),
        // On va delete le produit existant et créer un nouvel objet
         Produit ancienProduit = produitRepository.findById(produitToUpdate.getId()).orElse(null);
//                 .findProduitById(produitToUpdate.getId());

        List<Produit> listeProduits = ancienProduit.getCategorieProduit().getProduitId();

        // = 1
        List<Produit> result = categoriesRepository.getOne(ancienProduit.getId()).getProduitId();

        for (Produit prod : new ArrayList<>(listeProduits)) {
            if (prod.getId().equals(ancienProduit.getId())){
                categoriesRepository.getOne(ancienProduit.getId()).getProduitId().remove(prod);
            }
        }

        // = 0
        List<Produit> result2 = categoriesRepository.getOne(ancienProduit.getId()).getProduitId();


        Produit produit = produitRepository.getOne(ancienProduit.getId());

        produitRepository.deleteProduitById(ancienProduit.getId());

        Produit rrr = produitRepository.getOne(ancienProduit.getId());


        // Créer un nouvel objet : mapping des attributs et ajout des objets
        Produit newProduit = new Produit();
        newProduit.setCategorieProduit(produitToUpdate.getCategorieProduit());
        newProduit.setDescriptionProduit("bdd");

        newProduit
                .setSousCategorieProduit(produitToUpdate.getSousCategorieProduit());
        newProduit.setFournisseurProduit(produitToUpdate.getFournisseurProduit());
        newProduit.setFactureProduit(produitToUpdate.getFactureProduit());

        produitRepository.saveAndFlush(newProduit);
        return true;

//        Produit existingProduit = getProduitById(id);
//        if (existingProduit == null) {
//            LOGGER.error("Unknow produit for id: {}", id);
//            return false;
//        }
//        existingProduit.setCategorieProduit(produit.getCategorieProduit());
//        existingProduit
//                .setSousCategorieProduit(produit.getSousCategorieProduit());
//        existingProduit.setFournisseurProduit(produit.getFournisseurProduit());
//        existingProduit.setFactureProduit(produit.getFactureProduit());
//        existingProduit.setDescriptionProduit(produit.getDescriptionProduit());
//        existingProduit.setDateAchat(produit.getDateAchat());
//        existingProduit.setLieuAchat(produit.getLieuAchat());
//        existingProduit.setQuantite(produit.getQuantite());
//        existingProduit
//                .setPourcentageDeRemise(produit.getPourcentageDeRemise());
//        existingProduit
//                .setPrixAchatUnitaireTTC(produit.getPrixAchatUnitaireTTC());
//        existingProduit.setCommentaire(produit.getCommentaire());
//
//        Double prixFinalAvecRemise = PriceCalculator
//                .calculateFinalPriceWithDiscount(
//                        produit.getPrixAchatUnitaireTTC(),
//                        produit.getQuantite(),
//                        produit.getPourcentageDeRemise());
//        existingProduit.setPrixFinalAvecRemise(prixFinalAvecRemise);
//        produitRepository.save(existingProduit);
//        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    public boolean deleteProduitById(final Long produitId) {


        Produit existingProduit = getProduitById(produitId);

        if (existingProduit == null) {
            LOGGER.error("Unknow produit for id: {}", produitId);
            return false;
        }
        produitRepository.deleteById(produitId);

        return true;
    }
}
