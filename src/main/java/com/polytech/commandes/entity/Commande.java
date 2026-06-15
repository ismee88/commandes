package com.polytech.commandes.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "commande")
public class Commande {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime dateCommande;

    @Enumerated(EnumType.STRING)
    private StatusCommande status;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Client client;

    @OneToMany(
            mappedBy = "commande",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<LigneCommande> lignes;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private String createdBy;
}
