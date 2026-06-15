package com.polytech.commandes.repository;

import com.polytech.commandes.entity.Produit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface ProduitRepository extends JpaRepository<Produit, Long> {
    boolean existsByNom(String nom);

    @Query("""
    SELECT p 
    FROM Produit p 
    WHERE 
    (:minPrix IS NULL OR p.prix >= :minPrix)
    AND
    (:maxPrix IS NULL OR p.prix <= :maxPrix)
    AND
    (:stockMin IS NULL OR p.stock >= :stockMin)
    """)
    List<Produit> search(
            @Param("minPrix") BigDecimal minPrix,
            @Param("maxPrix") BigDecimal maxPrix,
            @Param("stockMin") Integer stockMin
    );

}
