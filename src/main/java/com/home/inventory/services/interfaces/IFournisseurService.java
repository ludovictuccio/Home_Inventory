package com.home.inventory.services.interfaces;

import com.home.inventory.entities.Fournisseur;

public interface IFournisseurService {

    /**
     * Method used to add new fournisseur.
     * 
     * @param fournisseur
     * @return Fournisseur
     */
    Fournisseur addFournisseur(Fournisseur fournisseur);

    /**
     * Method used to get a fournisseur by id.
     * 
     * @param id
     * @return Fournisseur
     */
    Fournisseur getFournisseurById(Long id);

    /**
     * Method used to update a fournisseur by description.
     * 
     * @param fournisseur
     * @param description
     * @return boolean
     */
    boolean updateFournisseurByDescription(Fournisseur fournisseur,
            String description);

    /**
     * Method used to update a fournisseur by id.
     * 
     * @param fournisseur
     * @param id
     * @return boolean
     */
    boolean updateFournisseurById(Fournisseur fournisseur, Long id);

    /**
     * Method used to delete a fournisseur by id.
     * 
     * @param fournisseurId
     * @return boolean
     */
    boolean deleteFournisseurById(Long fournisseurId);

}
