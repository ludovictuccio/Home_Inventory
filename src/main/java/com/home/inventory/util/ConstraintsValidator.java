package com.home.inventory.util;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.home.inventory.entities.Categories;

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

}
