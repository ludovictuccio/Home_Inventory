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
@Table(name = "fournisseur")
public class Fournisseur implements Serializable {

    private static final long serialVersionUID = 1242449377718142897L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank(message = "Description obligatoire")
    @Size(min = 2, max = 250, message = "Le mot doit faire au minimum 2 caractères.")
    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "fournisseurProduit", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Produit> produitId;

    public Fournisseur(Long id,
            @NotBlank(message = "Description obligatoire") @Size(min = 2, max = 250, message = "Le mot doit faire au minimum 2 caractères.") String fournisseurDescription) {
        super();
        this.id = id;
        this.description = fournisseurDescription;
    }

    public Fournisseur(
            @NotBlank(message = "Description obligatoire") @Size(min = 2, max = 250, message = "Le mot doit faire au minimum 2 caractères.") String fournisseurDescription) {
        super();
        this.description = fournisseurDescription;
    }

}
