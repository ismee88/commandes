package com.polytech.commandes.repository;

import com.polytech.commandes.entity.Commande;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface CommandeRepository extends JpaRepository<Commande, Long> {

    List<Commande> findByClientId(Long clientId);

    List<Commande> findByDateCommandeBetween(LocalDateTime debut, LocalDateTime fin);
}
