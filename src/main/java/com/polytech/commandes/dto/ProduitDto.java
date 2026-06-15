package com.polytech.commandes.dto;

import java.math.BigDecimal;

public record ProduitDto(
        Long id,
        String nom,
        BigDecimal prix,
        Integer stock
) {
}
