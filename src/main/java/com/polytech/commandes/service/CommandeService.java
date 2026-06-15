package com.polytech.commandes.service;

import com.polytech.commandes.dto.CommandeDto;
import com.polytech.commandes.dto.CreateCommandeDto;
import com.polytech.commandes.dto.LigneCommandeDto;
import com.polytech.commandes.entity.*;
import com.polytech.commandes.repository.ClientRepository;
import com.polytech.commandes.repository.CommandeRepository;
import com.polytech.commandes.repository.ProduitRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommandeService {

    private final CommandeRepository commandeRepository;
    private final ClientRepository clientRepository;
    private final ProduitRepository produitRepository;

    public CommandeDto create(CreateCommandeDto dto) {

        Client client = clientRepository.findById(dto.clientId()).orElseThrow();

        Commande c = new Commande();

        c.setClient(client);
        c.setDateCommande(LocalDateTime.now());
        c.setStatus(StatusCommande.CREATED);
        c.setCreatedAt(LocalDateTime.now());

        List<LigneCommande> lignes = dto.lignes()
                .stream()
                .map(ligneCommandeDto -> {

                    Produit p = produitRepository
                            .findById(ligneCommandeDto.produitId())
                            .orElseThrow();

                    LigneCommande l = new LigneCommande();

                    l.setProduit(p);
                    l.setQuantite(ligneCommandeDto.quantite());
                    l.setPrixUnitaire(p.getPrix());

                    l.setCommande(c);

                    return l;
                })
                .toList();

        c.setLignes(lignes);
        commandeRepository.save(c);

        return toDto(c);
    }

    public List<CommandeDto> findAll() {
        return commandeRepository.findAll()
                .stream()
                .map(this::toDto)
                .toList();
    }

    public CommandeDto findById(Long id) {
        Commande c = commandeRepository.findById(id).orElseThrow();
        return toDto(c);
    }

    public List<CommandeDto> findByClient(Long clientId){

        return commandeRepository.findByClientId(clientId)
                .stream()
                .map(this::toDto)
                .toList();
    }

    private CommandeDto toDto(Commande c) {
        List<LigneCommandeDto> lignes =
                c.getLignes()
                        .stream()
                        .map(ligne ->
                                new LigneCommandeDto(
                                        ligne.getProduit().getId(),
                                        ligne.getQuantite(),
                                        ligne.getPrixUnitaire()
                                ))
                        .toList();

        return new CommandeDto(
                c.getId(),
                c.getClient().getId(),
                c.getStatus().name(),
                c.getDateCommande(),
                lignes,
                calculerTotal(c)
        );
    }

    public BigDecimal calculerTotal(Commande c) {

        return c.getLignes()
                .stream()
                .map(ligne ->
                        ligne.getPrixUnitaire()
                                .multiply(
                                        BigDecimal.valueOf(
                                                ligne.getQuantite()
                                        )
                                )
                )
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Transactional
    public CommandeDto validercommande(Long commandeId){

        Commande c = commandeRepository.findById(commandeId).orElseThrow();

        if(c.getStatus() != StatusCommande.CREATED){
            throw new RuntimeException("Cette commande ne peut pas etre modifiee");
        }

        for(LigneCommande l : c.getLignes()){
            Produit produit = l.getProduit();

            if(produit.getStock() < l.getQuantite()){
                throw new RuntimeException("Stock insuffisant pour le produit : "+produit.getNom());
            }
        }

        for(LigneCommande l : c.getLignes()){
            Produit produit = l.getProduit();
            produit.setStock(produit.getStock() - l.getQuantite());

            produitRepository.save(produit);
        }

        c.setStatus(StatusCommande.VALIDATED);
        c.setUpdatedAt(LocalDateTime.now());

        commandeRepository.save(c);
        return toDto(c);
    }

    @Transactional
    public CommandeDto annulercommande(Long commandeId){

        Commande c = commandeRepository.findById(commandeId).orElseThrow();

        if(c.getStatus() == StatusCommande.VALIDATED){
            throw new RuntimeException("Une commande validee ne peut pas etre annulee");
        }

        if (c.getStatus() == StatusCommande.CANCELLED) {
            throw new RuntimeException("Commande deja annulee");
        }

        c.setStatus(StatusCommande.CANCELLED);
        c.setUpdatedAt(LocalDateTime.now());

        commandeRepository.save(c);

        return toDto(c);
    }

    public List<CommandeDto> findBetweenDates(LocalDateTime debut, LocalDateTime fin){
        return commandeRepository
                .findByDateCommandeBetween(debut, fin)
                .stream()
                .map(this::toDto)
                .toList();
    }

    public BigDecimal chiffreAffairesGlobal(){
        return commandeRepository.findAll()
                .stream()
                .filter(c ->
                        c.getStatus() == StatusCommande.VALIDATED
                )
                .map(this::calculerTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal totalCommandesClient(Long clientId){
        return commandeRepository
                .findByClientId(clientId)
                .stream()
                .filter(c -> c.getStatus() == StatusCommande.VALIDATED)
                .map(this::calculerTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

}
