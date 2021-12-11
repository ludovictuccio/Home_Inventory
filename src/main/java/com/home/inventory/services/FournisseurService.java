package com.home.inventory.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.home.inventory.entities.Fournisseur;
import com.home.inventory.repository.FournisseurRepository;
import com.home.inventory.util.ConstraintsValidator;

@Service
public class FournisseurService implements IFournisseurService {

    private static final Logger LOGGER = LoggerFactory
            .getLogger("FournisseurService");

    @Autowired
    private FournisseurRepository fournisseurRepository;

    /**
     * {@inheritDoc}
     */
    public Fournisseur addFournisseur(Fournisseur fournisseur) {

        if ((ConstraintsValidator.checkValidFournisseur(fournisseur) == null)
                || (fournisseurRepository.findFournisseurByDescription(
                        fournisseur.getDescription()) != null)) {
            return null;
        }
        fournisseurRepository.save(fournisseur);
        LOGGER.info("Succes new Fournisseur creation");
        return fournisseur;
    }

    /**
     * {@inheritDoc}
     */
    public Fournisseur getFournisseurById(final Long id) {
        return fournisseurRepository.findById(id).orElse(null);
    }

    /**
     * {@inheritDoc}
     */
    public boolean updateFournisseurByDescription(final Fournisseur fournisseur,
            final String description) {
        boolean isUpdated = false;

        Fournisseur existingFournisseur = fournisseurRepository
                .findFournisseurByDescription(description);

        if (existingFournisseur == null) {
            LOGGER.error("Unknow Fournisseur: {}", description);
            return isUpdated;
        }
        existingFournisseur.setDescription(fournisseur.getDescription());
        fournisseurRepository.save(existingFournisseur);
        isUpdated = true;
        return isUpdated;
    }

    /**
     * {@inheritDoc}
     */
    public boolean updateFournisseurById(final Fournisseur fournisseur,
            final Long id) {
        boolean isUpdated = false;

        Fournisseur existingFournisseur = fournisseurRepository.findById(id)
                .orElse(null);

        if (existingFournisseur == null) {
            LOGGER.error("Unknow Fournisseur for id: {}", id);
            return isUpdated;
        }
        existingFournisseur.setDescription(fournisseur.getDescription());
        fournisseurRepository.save(existingFournisseur);
        isUpdated = true;
        return isUpdated;
    }

    /**
     * {@inheritDoc}
     */
    public boolean deleteFournisseurById(final Long fournisseurId) {
        boolean isDeleted = false;

        Fournisseur existingFournisseur = fournisseurRepository
                .findById(fournisseurId).orElse(null);

        if (existingFournisseur == null) {
            LOGGER.error("Unknow fournisseur for id: {}", fournisseurId);
            return isDeleted;
        }
        fournisseurRepository.delete(existingFournisseur);
        isDeleted = true;
        return isDeleted;
    }

}
