package com.home.inventory.entities;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "sous_categories")
public class SousCategories implements Serializable {

    private static final long serialVersionUID = 2335052630704302352L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank(message = "Description obligatoire.")
    @Size(min = 2, max = 250, message = "Le mot doit faire au minimum 2 caractères.")
    @Column(name = "description")
    private String description;

    @OneToOne(cascade = { CascadeType.ALL,
            CascadeType.REMOVE }, fetch = FetchType.EAGER)
    @JoinColumn(name = "id", referencedColumnName = "id")
    private Produit produitId;

    public SousCategories(
            @NotBlank(message = "Description obligatoire.") @Size(min = 2, max = 250, message = "Le mot doit faire au minimum 2 caractères.") String souscatdescription) {
        super();
        this.description = souscatdescription;
    }

    public SousCategories(Long id,
            @NotBlank(message = "Description obligatoire.") @Size(min = 2, max = 250, message = "Le mot doit faire au minimum 2 caractères.") String souscatdescription) {
        super();
        this.id = id;
        this.description = souscatdescription;
    }

}
