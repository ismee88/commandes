package com.polytech.commandes.dto;

import java.util.List;

public record CreateCommandeDto(
        Long clientId,
        List<LigneCommandeDto> lignes
) {
}
