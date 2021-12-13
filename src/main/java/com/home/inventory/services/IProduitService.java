package com.home.inventory.services;

import com.home.inventory.entities.Produit;

public interface IProduitService {

    Produit addProduit(Produit produit);

    Produit getProduitById(Long id);

    boolean updateProduitById(Produit produit, Long id);

    boolean deleteProduitById(Long produitId);

}
