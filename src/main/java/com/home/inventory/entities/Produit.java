package com.home.inventory.entities;

import java.io.File;
import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.*;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

@EqualsAndHashCode
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "produit")
public class Produit implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "categories_id")
    private Categories categorieProduit;

    @ManyToOne
    @JoinColumn(name = "sous_categories_id")
    private SousCategories sousCategorieProduit;

    @ManyToOne
    @JoinColumn(name = "fournisseur_id")
    private Fournisseur fournisseurProduit;

    @ManyToOne
    @JoinColumn(name = "facture_id")
    private Facture factureProduit;

    @NotBlank(message = "Description obligatoire")
    @Column(name = "description")
    private String descriptionProduit;

    @Column(name = "date_achat")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateAchat;

    @Column(name = "lieu_achat")
    private String lieuAchat;

    @PositiveOrZero
    @Column(name = "quantite")
    private double quantite;

    @PositiveOrZero
    @DecimalMax(value = "100.0")
    @Column(name = "pourcent_remise")
    private double pourcentageDeRemise;

    @PositiveOrZero
    @Column(name = "prix_achat_unitaire_ttc")
    private double prixAchatUnitaireTTC;

    @PositiveOrZero
    @Column(name = "prix_achat_total_avec_remise_ttc")
    private double prixFinalAvecRemise;

    @Column(name = "commentaire")
    private String commentaire;

//    @Column(name = "document_photo")
//    private File documentEtPhoto;

    public Produit(Categories categorieProduit,
            SousCategories sousCategorieProduit, Fournisseur fournisseurProduit,
            Facture facture,
            @NotBlank(message = "Description obligatoire") String description,
            LocalDate dateAchat, String lieuAchat,
            @PositiveOrZero double quantite,
            @PositiveOrZero @DecimalMax("100.0") double pourcentageDeRemise,
            @PositiveOrZero double prixAchatUnitaireTTC, String commentaire) {
        super();
        this.categorieProduit = categorieProduit;
        this.sousCategorieProduit = sousCategorieProduit;
        this.fournisseurProduit = fournisseurProduit;
        this.factureProduit = facture;
        this.descriptionProduit = description;
        this.dateAchat = dateAchat;
        this.lieuAchat = lieuAchat;
        this.quantite = quantite;
        this.pourcentageDeRemise = pourcentageDeRemise;
        this.prixAchatUnitaireTTC = prixAchatUnitaireTTC;
        this.commentaire = commentaire;
    }

//    public Produit(Long idProduit, Categories categorieProduit,
//            SousCategories sousCategorieProduit, Fournisseur fournisseurProduit,
//            Facture facture,
//            @NotBlank(message = "Description obligatoire") String description,
//            LocalDate dateAchat, String lieuAchat,
//            @PositiveOrZero double quantite,
//            @PositiveOrZero @DecimalMax("100.0") double pourcentageDeRemise,
//            @PositiveOrZero double prixAchatUnitaireTTC, String commentaire) {
//        super();
//        this.id = idProduit;
//        this.categorieProduit = categorieProduit;
//        this.sousCategorieProduit = sousCategorieProduit;
//        this.fournisseurProduit = fournisseurProduit;
//        this.factureProduit = facture;
//        this.description = description;
//        this.dateAchat = dateAchat;
//        this.lieuAchat = lieuAchat;
//        this.quantite = quantite;
//        this.pourcentageDeRemise = pourcentageDeRemise;
//        this.prixAchatUnitaireTTC = prixAchatUnitaireTTC;
//        this.commentaire = commentaire;
//    }

//    public String form;
//
//    public String getForm() {
//        return form;
//    }
//
//    public void setForm(String form) {
//        this.form = form;
//    }
}
