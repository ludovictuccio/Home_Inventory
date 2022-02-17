package com.home.inventory.services.interfaces;

import com.home.inventory.entities.Facture;

public interface IFactureService {

    /**
     * Method used to add Facture.
     *
     * @param facture
     * @return Facture
     */
    Facture addFacture(Facture facture);

    /**
     * Method used to get a Facture by id.
     *
     * @param id
     * @return Facture
     */
    Facture getFactureById(Long id);

    /**
     * Method used to update a Facture by description.
     *
     * @param facture
     * @param description
     * @return boolean
     */
    boolean updateFactureByDescription(Facture facture, String description);

    /**
     * Method used to update a Facture by id.
     *
     * @param facture
     * @param id
     * @return boolean
     */
    boolean updateFactureById(Facture facture, Long id);

    /**
     * Method used to delete a Facture by id.
     *
     * @param factureId
     * @return boolean
     */
    boolean deleteFactureById(Long factureId);

}
