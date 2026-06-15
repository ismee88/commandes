package com.polytech.commandes.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record CommandeDto(
        Long id,
        Long clientId,
        String status,
        LocalDateTime dateCommande,
        List<LigneCommandeDto> lignes,
        BigDecimal total
) {
}
