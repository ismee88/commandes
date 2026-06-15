package com.polytech.commandes.dto;

import java.math.BigDecimal;

public record LigneCommandeDto(
        Long produitId,
        Integer quantite,
        BigDecimal prixUnitaire
) {
}
