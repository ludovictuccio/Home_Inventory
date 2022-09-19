package com.home.inventory.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.home.inventory.entities.Produit;
import com.home.inventory.repository.ProduitRepository;
import com.home.inventory.services.interfaces.IProduitService;
import com.home.inventory.util.ConstraintsValidator;
import com.home.inventory.util.PriceCalculator;

@Service
public class ProduitService implements IProduitService {

    private static final Logger LOGGER = LoggerFactory
            .getLogger("ProduitService");

    @Autowired
    private ProduitRepository produitRepository;

    /**
     * {@inheritDoc}
     */
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
    public boolean updateProduitById(final Produit produit, final Long id) {
        boolean isUpdated = false;

        Produit existingProduit = getProduitById(id);

        if (existingProduit == null) {
            LOGGER.error("Unknow produit for id: {}", id);
            return isUpdated;
        }
        existingProduit.setCategorieProduit(produit.getCategorieProduit());
        existingProduit
                .setSousCategorieProduit(produit.getSousCategorieProduit());
        existingProduit.setFournisseurProduit(produit.getFournisseurProduit());
        existingProduit.setFactureProduit(produit.getFactureProduit());
        existingProduit.setDescriptionProduit(produit.getDescriptionProduit());
        existingProduit.setDateAchat(produit.getDateAchat());
        existingProduit.setLieuAchat(produit.getLieuAchat());
        existingProduit.setQuantite(produit.getQuantite());
        existingProduit
                .setPourcentageDeRemise(produit.getPourcentageDeRemise());
        existingProduit
                .setPrixAchatUnitaireTTC(produit.getPrixAchatUnitaireTTC());
        existingProduit.setCommentaire(produit.getCommentaire());

        Double prixFinalAvecRemise = PriceCalculator
                .calculateFinalPriceWithDiscount(
                        produit.getPrixAchatUnitaireTTC(),
                        produit.getQuantite(),
                        produit.getPourcentageDeRemise());
        existingProduit.setPrixFinalAvecRemise(prixFinalAvecRemise);

        produitRepository.save(existingProduit);
        isUpdated = true;
        return isUpdated;
    }

    /**
     * {@inheritDoc}
     */
    public boolean deleteProduitById(final Long produitId) {
        boolean isDeleted = false;

        Produit existingProduit = getProduitById(produitId);

        if (existingProduit == null) {
            LOGGER.error("Unknow produit for id: {}", produitId);
            return isDeleted;
        }
        produitRepository.deleteById(produitId);
        isDeleted = true;
        return isDeleted;
    }
}
