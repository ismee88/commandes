package com.polytech.commandes.controller;

import com.polytech.commandes.dto.CommandeDto;
import com.polytech.commandes.dto.CreateCommandeDto;
import com.polytech.commandes.service.CommandeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/commandes")
@RequiredArgsConstructor
@Tag(
        name = "Commandes",
        description = "Gestion des commandes clients"
)
public class CommandeController {

    private final CommandeService commandeService;

    @Operation(
            summary = "Créer une commande",
            description = "Permet de créer une nouvelle commande pour un client"
    )
    @PostMapping
    public CommandeDto create(@RequestBody CreateCommandeDto dto) {
        return commandeService.create(dto);
    }

    @Operation(
            summary = "Lister les commandes",
            description = "Retourne toutes les commandes enregistrées"
    )
    @GetMapping
    public List<CommandeDto> findAll() {
        return commandeService.findAll();
    }

    @Operation(
            summary = "Rechercher une commande par ID",
            description = "Retourne les informations d'une commande à partir de son identifiant"
    )
    @GetMapping("/{id}")
    public CommandeDto findById(@PathVariable Long id) {
        return commandeService.findById(id);
    }

    @Operation(
            summary = "Lister les commandes d'un client",
            description = "Retourne toutes les commandes associées à un client"
    )
    @GetMapping("/client/{clientId}")
    public List<CommandeDto> findByClient(@PathVariable Long clientId) {
        return commandeService.findByClient(clientId);
    }

    @Operation(
            summary = "Valider une commande",
            description = "Valide une commande et décrémente le stock des produits"
    )
    @PutMapping("/{id}/validate")
    public CommandeDto validate(@PathVariable Long id) {
        return commandeService.validercommande(id);
    }

    @Operation(
            summary = "Annuler une commande",
            description = "Annule une commande si elle n'a pas encore été validée"
    )
    @PutMapping("/{id}/cancel")
    public CommandeDto cancel(@PathVariable Long id) {
        return commandeService.annulercommande(id);
    }

    @Operation(
            summary = "Rechercher les commandes entre deux dates",
            description = "Retourne toutes les commandes comprises entre une date de début et une date de fin"
    )
    @GetMapping("/entre-dates")
    public List<CommandeDto> entreDates(@RequestParam LocalDateTime debut, @RequestParam LocalDateTime fin) {
        return commandeService.findBetweenDates(debut, fin);
    }

    @Operation(
            summary = "Calculer le chiffre d'affaires global",
            description = "Retourne le chiffre d'affaires total des commandes validées"
    )
    @GetMapping("/statistiques/chiffre-affaires")
    public BigDecimal chiffreAffaires() {
        return commandeService.chiffreAffairesGlobal();
    }

    @Operation(
            summary = "Calculer le total des commandes d'un client",
            description = "Retourne le montant total des commandes validées d'un client"
    )
    @GetMapping("/client/{clientId}/total")
    public BigDecimal totalClient(@PathVariable Long clientId) {
        return commandeService.totalCommandesClient(clientId);
    }
}
