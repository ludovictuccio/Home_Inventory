package com.home.inventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.home.inventory.entities.Fournisseur;

@Repository
public interface FournisseurRepository
        extends JpaRepository<Fournisseur, Long> {

    Fournisseur findFournisseurById(Long FournisseurId);

    Fournisseur findFournisseurByDescription(String description);
}
