package com.home.inventory.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.home.inventory.entities.Facture;
import com.home.inventory.repository.FactureRepository;
import com.home.inventory.services.interfaces.IFactureService;
import com.home.inventory.util.ConstraintsValidator;

@Service
public class FactureService implements IFactureService {

    private static final Logger LOGGER = LoggerFactory
            .getLogger("FactureService");

    @Autowired
    private FactureRepository factureRepository;

    /**
     * {@inheritDoc}
     */
    public Facture addFacture(final Facture facture) {

        if ((ConstraintsValidator.checkValidFacture(facture) == null)
                || (factureRepository.findFactureByDescription(
                        facture.getDescription()) != null)) {
            return null;
        }
        factureRepository.save(facture);
        LOGGER.info("Succes new facture creation");
        return facture;
    }

    /**
     * {@inheritDoc}
     */
    public Facture getFactureById(final Long id) {
        return factureRepository.findById(id).orElse(null);
    }

    /**
     * {@inheritDoc}
     */
    public boolean updateFactureByDescription(final Facture facture,
            final String description) {
        boolean isUpdated = false;

        Facture existingFacture = factureRepository
                .findFactureByDescription(description);

        if (existingFacture == null) {
            LOGGER.error("Unknow facture: {}", description);
            return isUpdated;
        }
        existingFacture.setDescription(facture.getDescription());
        factureRepository.save(existingFacture);
        isUpdated = true;
        return isUpdated;
    }

    /**
     * {@inheritDoc}
     */
    public boolean updateFactureById(final Facture facture, final Long id) {
        boolean isUpdated = false;

        Facture existingFacture = factureRepository.findById(id).orElse(null);

        if (existingFacture == null) {
            LOGGER.error("Unknow facture for id: {}", id);
            return isUpdated;
        }
        existingFacture.setDescription(facture.getDescription());
        factureRepository.save(existingFacture);
        isUpdated = true;
        return isUpdated;
    }

    /**
     * {@inheritDoc}
     */
    public boolean deleteFactureById(final Long factureId) {
        boolean isDeleted = false;

        Facture existingFacture = factureRepository.findById(factureId)
                .orElse(null);

        if (existingFacture == null) {
            LOGGER.error("Unknow facture for id: {}", factureId);
            return isDeleted;
        }
        factureRepository.delete(existingFacture);
        isDeleted = true;
        return isDeleted;
    }

}
