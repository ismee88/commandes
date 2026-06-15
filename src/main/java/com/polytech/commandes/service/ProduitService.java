package com.polytech.commandes.service;

import com.polytech.commandes.dto.CreateProduitDto;
import com.polytech.commandes.dto.ProduitDto;
import com.polytech.commandes.entity.Produit;
import com.polytech.commandes.exception.ApiException;
import com.polytech.commandes.repository.ProduitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProduitService {
    private final ProduitRepository produitRepository;

    public ProduitDto create(CreateProduitDto dto){

        if(produitRepository.existsByNom(dto.nom())){
            throw new ApiException(409, "Produit deja existant");
        }

        Produit p = new Produit();

        p.setNom(dto.nom());
        p.setPrix(dto.prix());
        p.setStock(dto.stock());

        p = produitRepository.save(p);

        return new ProduitDto(
                p.getId(),
                p.getNom(),
                p.getPrix(),
                p.getStock()
        );
    }

    public List<ProduitDto> findAll(){

        return produitRepository.findAll()
                .stream()
                .map(produit -> new ProduitDto(
                        produit.getId(),
                        produit.getNom(),
                        produit.getPrix(),
                        produit.getStock()
                ))
                .toList();
    }

    public ProduitDto findById(Long id){

        Produit produit = produitRepository.findById(id)
                .orElseThrow(() ->
                        new ApiException(404, "Produit introuvable"));

        return new ProduitDto(
                produit.getId(),
                produit.getNom(),
                produit.getPrix(),
                produit.getStock()
        );
    }

    public ProduitDto update(Long id, CreateProduitDto dto){

        Produit p = produitRepository.findById(id)
                .orElseThrow(() ->
                        new ApiException(404, "Produit introuvable"));

        p.setNom(dto.nom());
        p.setPrix(dto.prix());
        p.setStock(dto.stock());

        p = produitRepository.save(p);

        return new ProduitDto(
                p.getId(),
                p.getNom(),
                p.getPrix(),
                p.getStock()
        );
    }

    public void delete(Long id){

        Produit produit = produitRepository.findById(id)
                .orElseThrow(() ->
                        new ApiException(404, "Produitintrouvable"));

        produitRepository.delete(produit);
    }

    public List<ProduitDto> search(BigDecimal minPrix, BigDecimal maxPrix, Integer stockMin) {
        return produitRepository
                .search(minPrix, maxPrix, stockMin)
                .stream()
                .map(produit ->
                        new ProduitDto(
                                produit.getId(),
                                produit.getNom(),
                                produit.getPrix(),
                                produit.getStock()
                        ))
                .toList();
    }

    public Page<ProduitDto> findAllPaged(int page, int size){
        Pageable pageable = PageRequest.of(page, size);

        return produitRepository
                .findAll(pageable)
                .map(produit ->
                        new ProduitDto(
                                produit.getId(),
                                produit.getNom(),
                                produit.getPrix(),
                                produit.getStock()
                        ));
    }

    public Page<ProduitDto> findAllPagedAnddSorted(int page, int size, String sortBy){
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));

        return produitRepository
                .findAll(pageable)
                .map(produit ->
                        new ProduitDto(
                                produit.getId(),
                                produit.getNom(),
                                produit.getPrix(),
                                produit.getStock()
                        ));
    }
}
