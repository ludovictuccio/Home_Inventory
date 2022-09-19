package com.home.inventory.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@EqualsAndHashCode
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "sous_categories")
public class SousCategories implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank(message = "Description obligatoire.")
    @Size(min = 2, max = 250, message = "Le mot doit faire au minimum 2 caractères.")
    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "sousCategorieProduit", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Produit> produitId;

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
