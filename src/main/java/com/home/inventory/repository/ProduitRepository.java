package com.home.inventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.home.inventory.entities.Produit;

@Repository
public interface ProduitRepository extends JpaRepository<Produit, Long> {

    Produit findProduitById(Long produitId);

}
