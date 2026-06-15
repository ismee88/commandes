package com.polytech.commandes.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "token")
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 500)
    private String token;

    private Date expiredDate;

    private Boolean revoked = false;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Utilisateur utilisateur;
}
