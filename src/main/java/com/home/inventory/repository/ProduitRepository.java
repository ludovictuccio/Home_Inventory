package com.home.inventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.home.inventory.entities.Facture;
import com.home.inventory.entities.Produit;

import javax.transaction.Transactional;

@Repository
public interface ProduitRepository extends JpaRepository<Produit, Long> {

    Produit findProduitById(Long id);

    Produit findProduitByFactureProduit(Facture factureProduit);

    @Modifying
    @Transactional
    @Query("delete from Produit p where p.id = ?1")
    void deleteProduitById(Long id);

}
