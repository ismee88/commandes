package com.polytech.commandes.dto;

import java.math.BigDecimal;

public record CreateProduitDto(
        String nom,
        BigDecimal prix,
        Integer stock
) {
}
