package com.polytech.commandes.repository;

import com.polytech.commandes.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
