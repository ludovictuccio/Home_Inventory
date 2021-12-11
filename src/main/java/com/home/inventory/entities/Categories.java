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
@Table(name = "categories")
public class Categories implements Serializable {

    private static final long serialVersionUID = 1306648345214910367L;

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

    public Categories(
            @NotBlank(message = "Description obligatoire") String descriptionCategorie) {
        super();
        this.description = descriptionCategorie;
    }

    public Categories(final Long catId,
            @NotBlank(message = "Description obligatoire") String descriptionCategorie) {
        super();
        this.id = catId;
        this.description = descriptionCategorie;
    }

}
