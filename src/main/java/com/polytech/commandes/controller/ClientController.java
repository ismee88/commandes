package com.polytech.commandes.controller;

import com.polytech.commandes.dto.ClientDto;
import com.polytech.commandes.dto.CreateClientDto;
import com.polytech.commandes.service.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clients")
@RequiredArgsConstructor
@Tag(
        name = "Clients",
        description = "Gestion des clients"
)
public class ClientController {

    private final ClientService clientService;

    @Operation(
            summary = "Créer un client",
            description = "Ajoute un nouveau client dans la base de données"
    )
    @PostMapping
    public ResponseEntity<ClientDto> create(@RequestBody CreateClientDto dto){

        return ResponseEntity
                .status(201)
                .body(clientService.create(dto));
    }

    @Operation(
            summary = "Lister tous les clients",
            description = "Retourne la liste complète des clients"
    )
    @GetMapping
    public List<ClientDto> findAll(){
        return clientService.findAll();
    }

    @Operation(
            summary = "Rechercher un client par ID",
            description = "Retourne les informations d'un client à partir de son identifiant"
    )
    @GetMapping("/{id}")
    public ClientDto findById(@PathVariable long id){
        return clientService.findById(id);
    }

    @Operation(
            summary = "Modifier un client",
            description = "Met à jour les informations d'un client existant"
    )
    @PutMapping("/{id}")
    public ClientDto update(@PathVariable long id, @RequestBody CreateClientDto dto){
        return clientService.update(id, dto);
    }

    @Operation(
            summary = "Supprimer un client",
            description = "Supprime un client de la base de données"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id){

        clientService.delete(id);

        return ResponseEntity.noContent().build();
    }
}
