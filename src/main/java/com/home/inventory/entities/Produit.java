package com.home.inventory.entities;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "produit")
public class Produit implements Serializable {

    private static final long serialVersionUID = -5987711516877850452L;

    @Id
    @Column(name = "id", unique = true, nullable = false)
    private Long id = System.nanoTime();

    private Categories categorie;

    private SousCategories sousCategorie;

    private Fournisseur fournisseur;

    @NotBlank(message = "Description obligatoire")
    @Column(name = "description")
    private String description;

    @Column(name = "date_achat")
    private LocalDateTime dateAchat;

    @Column(name = "lieu_achat")
    private String lieuAchat;

    @Column(name = "no_facture")
    private String noFacture;

    @Column(name = "quantite")
    private double quantite;

    @Column(name = "pourcent_remise")
    private double pourcentageDeRemise;

    @Column(name = "prix_achat_unitaire_ttc")
    private double prixAchatUnitaireTTC;

    @Column(name = "commentaire")
    private String commentaire;

    @Column(name = "document_photo")
    private String documentEtPhoto;
}
