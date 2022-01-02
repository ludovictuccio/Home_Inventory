package com.home.inventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.home.inventory.entities.Facture;

@Repository
public interface FactureRepository extends JpaRepository<Facture, Long> {

    Facture findFactureById(Long factureId);

    Facture findFactureByDescription(String description);

}
