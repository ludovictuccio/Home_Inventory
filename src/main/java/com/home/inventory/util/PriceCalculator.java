package com.home.inventory.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PriceCalculator {

    private static final Logger LOGGER = LogManager
            .getLogger("PriceCalculator");

    public static Double calculateFinalPriceWithDiscount(
            final Double prixAchatUnitaireTTC, final Double quantite,
            final Double pourcentageDeRemise) {

        Double prixFinalAvecRemise = (prixAchatUnitaireTTC * quantite)
                * ((100 - pourcentageDeRemise) / 100);

        return prixFinalAvecRemise;
    }

}
