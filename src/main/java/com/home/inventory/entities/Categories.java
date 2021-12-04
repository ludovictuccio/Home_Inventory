package com.home.inventory.entities;

import java.io.Serializable;

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
@Table(name = "categories")
public class Categories implements Serializable {

    private static final long serialVersionUID = -5987711516877850452L;

    @Id
    @Column(name = "id", unique = true, nullable = false)
    private Long id = System.nanoTime();

    @NotBlank(message = "Description obligatoire")
    @Column(name = "description")
    private String description;

}
