package com.polytech.commandes.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "ligne_commande")
public class LigneCommande {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer quantite;

    private BigDecimal prixUnitaire;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Produit produit;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Commande commande;
}
