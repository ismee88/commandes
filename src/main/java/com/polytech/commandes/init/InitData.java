package com.polytech.commandes.init;

import com.polytech.commandes.entity.*;
import com.polytech.commandes.repository.ClientRepository;
import com.polytech.commandes.repository.CommandeRepository;
import com.polytech.commandes.repository.ProduitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Component
@Profile("dev")
@RequiredArgsConstructor
public class InitData implements CommandLineRunner {
    private final ProduitRepository produitRepository;
    private final CommandeRepository commandeRepository;
    private final ClientRepository clientRepository;

    @Override
    public void run(String... args) throws Exception {
        if(produitRepository.count() > 0) {
            return;
        }

        Produit p1 = new Produit();
        p1.setNom("PC Portable");
        p1.setPrix(new BigDecimal("500000"));
        p1.setStock(20);

        Produit p2 = new Produit();
        p2.setNom("Clavier Gamer");
        p2.setPrix(new BigDecimal("30000"));
        p2.setStock(50);

        Produit p3 = new Produit();
        p3.setNom("Souris Gamer");
        p3.setPrix(new BigDecimal("25000"));
        p3.setStock(40);

        produitRepository.saveAll(List.of(p1, p2, p3));

        Client c1 = new Client();
        c1.setNom("Fall");
        c1.setEmail("fall@test.com");

        Client c2 = new Client();
        c2.setNom("Ndiaye");
        c2.setEmail("ndiaye@test.com");

        clientRepository.saveAll(List.of(c1, c2));

        Commande commande = new Commande();
        commande.setClient(c1);
        commande.setStatus(StatusCommande.CREATED);
        commande.setDateCommande(LocalDateTime.now());

        LigneCommande ligne = new LigneCommande();
        ligne.setProduit(p1);
        ligne.setQuantite(2);
        ligne.setPrixUnitaire(p1.getPrix());
        ligne.setCommande(commande);

        commande.setLignes(List.of(ligne));

        commandeRepository.save(commande);

        System.out.println("Initialisation DEV terminee");
    }
}
