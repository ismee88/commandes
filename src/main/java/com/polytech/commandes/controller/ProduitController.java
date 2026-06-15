package com.polytech.commandes.controller;

import com.polytech.commandes.dto.CreateProduitDto;
import com.polytech.commandes.dto.ProduitDto;
import com.polytech.commandes.entity.Produit;
import com.polytech.commandes.service.ProduitService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/produits")
@RequiredArgsConstructor
@Tag(
        name = "Produits",
        description = "Gestion du catalogue de produits"
)
public class ProduitController {

    private final ProduitService produitService;

    @Operation(
            summary = "Créer un produit",
            description = "Ajoute un nouveau produit dans le catalogue"
    )
    @PostMapping
    public ResponseEntity<ProduitDto> create(
            @RequestBody CreateProduitDto dto) {

        return ResponseEntity
                .status(201)
                .body(produitService.create(dto));
    }

    @Operation(
            summary = "Lister tous les produits",
            description = "Retourne la liste complète des produits"
    )
    @GetMapping
    public List<ProduitDto> findAll() {
        return produitService.findAll();
    }

    @Operation(
            summary = "Rechercher un produit par ID",
            description = "Retourne les informations d'un produit à partir de son identifiant"
    )
    @GetMapping("/{id}")
    public ProduitDto findById(@PathVariable Long id) {
        return produitService.findById(id);
    }

    @Operation(
            summary = "Modifier un produit",
            description = "Met à jour les informations d'un produit existant"
    )
    @PutMapping("/{id}")
    public ProduitDto update(
            @PathVariable Long id,
            @RequestBody CreateProduitDto dto) {

        return produitService.update(id, dto);
    }

    @Operation(
            summary = "Supprimer un produit",
            description = "Supprime un produit à partir de son identifiant"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        produitService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Recherche multicritères",
            description = "Recherche des produits selon un prix minimum, un prix maximum et un stock minimum"
    )
    @GetMapping("/search")
    public List<ProduitDto> search(
            @RequestParam(required = false)
            BigDecimal minPrix,

            @RequestParam(required = false)
            BigDecimal maxPrix,

            @RequestParam(required = false)
            Integer stockMin
    ){
        return produitService.search(minPrix, maxPrix, stockMin);
    }

    @Operation(
            summary = "Pagination des produits",
            description = "Retourne les produits sous forme paginée"
    )
    @GetMapping("/page")
    public Page<ProduitDto> page(
            @RequestParam(defaultValue = "0")
            int page,

            @RequestParam(defaultValue = "5")
            int size
    ){
        return produitService.findAllPaged(page, size);
    }

    @Operation(
            summary = "Pagination et tri des produits",
            description = "Retourne les produits paginés et triés selon un champ donné"
    )
    @GetMapping("/page-sort")
    public Page<ProduitDto> pageSort(
            @RequestParam(defaultValue = "0")
            int page,

            @RequestParam(defaultValue = "5")
            int size,

            @RequestParam(defaultValue = "nom")
            String sort
    ){
        return produitService.findAllPagedAnddSorted(page, size, sort);
    }
}
