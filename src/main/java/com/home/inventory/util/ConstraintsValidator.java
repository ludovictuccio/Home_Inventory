package com.home.inventory.util;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.home.inventory.entities.Categories;
import com.home.inventory.entities.Fournisseur;
import com.home.inventory.entities.Produit;
import com.home.inventory.entities.SousCategories;
import com.home.inventory.entities.User;

public class ConstraintsValidator {

    private static final Logger LOGGER = LogManager
            .getLogger("ConstraintsValidator");

    public static Categories checkValidCategory(final Categories category) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Categories>> constraintViolations = validator
                .validate(category);
        if (constraintViolations.size() > 0) {
            for (ConstraintViolation<Categories> contraintes : constraintViolations) {
                LOGGER.error(contraintes.getRootBeanClass().getSimpleName()
                        + "." + contraintes.getPropertyPath() + " "
                        + contraintes.getMessage());
            }
            return null;
        }
        return category;
    }

    public static SousCategories checkValidSousCategory(
            final SousCategories sousCategory) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<SousCategories>> constraintViolations = validator
                .validate(sousCategory);
        if (constraintViolations.size() > 0) {
            for (ConstraintViolation<SousCategories> contraintes : constraintViolations) {
                LOGGER.error(contraintes.getRootBeanClass().getSimpleName()
                        + "." + contraintes.getPropertyPath() + " "
                        + contraintes.getMessage());
            }
            return null;
        }
        return sousCategory;
    }

    public static Fournisseur checkValidFournisseur(
            final Fournisseur fournisseur) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Fournisseur>> constraintViolations = validator
                .validate(fournisseur);
        if (constraintViolations.size() > 0) {
            for (ConstraintViolation<Fournisseur> contraintes : constraintViolations) {
                LOGGER.error(contraintes.getRootBeanClass().getSimpleName()
                        + "." + contraintes.getPropertyPath() + " "
                        + contraintes.getMessage());
            }
            return null;
        }
        return fournisseur;
    }

    public static User checkValidUser(final User user) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<User>> constraintViolations = validator
                .validate(user);
        if (constraintViolations.size() > 0) {
            LOGGER.error(
                    "ERROR: a constraint was violated. Please check the informations entered.");
            for (ConstraintViolation<User> contraintes : constraintViolations) {
                LOGGER.error(contraintes.getRootBeanClass().getSimpleName()
                        + "." + contraintes.getPropertyPath() + " "
                        + contraintes.getMessage());
            }
            return null;
        } else if (!user.getRole().equalsIgnoreCase("USER")
                && !user.getRole().equalsIgnoreCase("ADMIN")) {
            LOGGER.error("ERROR: the role must be 'admin' or 'user'");
            return null;
        }
        return user;
    }

    public static Produit checkValidProduit(final Produit produit) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Produit>> constraintViolations = validator
                .validate(produit);
        if (constraintViolations.size() > 0) {
            for (ConstraintViolation<Produit> contraintes : constraintViolations) {
                LOGGER.error(contraintes.getRootBeanClass().getSimpleName()
                        + "." + contraintes.getPropertyPath() + " "
                        + contraintes.getMessage());
            }
            return null;
        }
        return produit;
    }

}
