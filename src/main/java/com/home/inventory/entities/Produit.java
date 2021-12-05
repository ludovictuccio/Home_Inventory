package com.home.inventory.entities;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
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

    private static final long serialVersionUID = 2024556901701412133L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

//    @ManyToOne
//    @JoinColumn(name = "categories_id")
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "produitId", fetch = FetchType.EAGER)
    private Categories categorieProduit;

//    @ManyToOne
//    @JoinColumn(name = "sous_categories_id")
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "produitId", fetch = FetchType.EAGER)
    private SousCategories sousCategorieProduit;

//    @ManyToOne
//    @JoinColumn(name = "fournisseur_id")
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "produitId", fetch = FetchType.EAGER)
    private Fournisseur fournisseurProduit;

    @NotBlank(message = "Description obligatoire")
    @Column(name = "description")
    private String description;

    @Column(name = "date_achat")
    private LocalDate dateAchat;

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

    /**
     * @param categorieProduit
     * @param sousCategorieProduit
     * @param fournisseurProduit
     * @param description
     * @param dateAchat
     * @param lieuAchat
     * @param noFacture
     * @param quantite
     * @param pourcentageDeRemise
     * @param prixAchatUnitaireTTC
     * @param commentaire
     */
    public Produit(Categories categorieProd, SousCategories sousCategorieProd,
            Fournisseur fournisseurProd,
            @NotBlank(message = "Description obligatoire") String descriptionProd,
            LocalDate dateAchatProd, String lieuAchatProd, String noFactureProd,
            double qte, double pourcentageRemise,
            double prixAchatUnitaireTTCProd, String comm) {
        super();
        this.categorieProduit = categorieProd;
        this.sousCategorieProduit = sousCategorieProd;
        this.fournisseurProduit = fournisseurProd;
        this.description = descriptionProd;
        this.dateAchat = dateAchatProd;
        this.lieuAchat = lieuAchatProd;
        this.noFacture = noFactureProd;
        this.quantite = qte;
        this.pourcentageDeRemise = pourcentageRemise;
        this.prixAchatUnitaireTTC = prixAchatUnitaireTTCProd;
        this.commentaire = comm;
    }

}
