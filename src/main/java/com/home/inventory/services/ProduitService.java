package com.home.inventory.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.home.inventory.entities.Produit;
import com.home.inventory.repository.ProduitRepository;
import com.home.inventory.util.ConstraintsValidator;

@Service
public class ProduitService implements IProduitService {

    private static final Logger LOGGER = LoggerFactory
            .getLogger("ProduitService");

    @Autowired
    private ProduitRepository produitRepository;

    /**
     * {@inheritDoc}
     */
    public Produit addProduit(Produit produit) {

        if ((ConstraintsValidator.checkValidProduit(produit) == null)
                || (produitRepository.findProduitByNoFacture(
                        produit.getNoFacture()) != null)) {
            LOGGER.error(
                    "Le no de facture est déjà existant. Prière de vérifier qu'il ne soit pas en doublon.");
            return null;
        }
        produitRepository.save(produit);
        LOGGER.info("Succes new produit creation");
        return produit;
    }

    /**
     * {@inheritDoc}
     */
    public Produit getProduitById(final Long id) {
        return produitRepository.findById(id).orElse(null);
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
        existingProduit.setDescription(produit.getDescription());
        existingProduit.setDateAchat(produit.getDateAchat());
        existingProduit.setLieuAchat(produit.getLieuAchat());
        existingProduit.setNoFacture(produit.getNoFacture());
        existingProduit.setQuantite(produit.getQuantite());
        existingProduit
                .setPourcentageDeRemise(produit.getPourcentageDeRemise());
        existingProduit
                .setPrixAchatUnitaireTTC(produit.getPrixAchatUnitaireTTC());
        existingProduit.setCommentaire(produit.getCommentaire());
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
        produitRepository.delete(existingProduit);
        isDeleted = true;
        return isDeleted;
    }
}