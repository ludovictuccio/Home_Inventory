package com.home.inventory.services.interfaces;

import com.home.inventory.entities.Produit;

public interface IProduitService {

    Produit addProduit(Produit produit);

    Produit getProduitById(Long id);

    boolean updateProduitById(final Produit produitToUpdate);

    boolean deleteProduitById(Long produitId);

}
