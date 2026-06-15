package com.polytech.commandes.service;

import com.polytech.commandes.dto.ClientDto;
import com.polytech.commandes.dto.CreateClientDto;
import com.polytech.commandes.entity.Client;
import com.polytech.commandes.exception.ApiException;
import com.polytech.commandes.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;

    public ClientDto create(CreateClientDto dto){

        Client client = new Client();

        client.setNom(dto.nom());
        client.setEmail(dto.email());

        client = clientRepository.save(client);

        return new ClientDto(
                client.getId(),
                client.getNom(),
                client.getEmail()
        );
    }

    public List<ClientDto> findAll(){

        return clientRepository.findAll()
                .stream()
                .map(client -> new ClientDto(
                        client.getId(),
                        client.getNom(),
                        client.getEmail()
                ))
                .toList();
    }

    public ClientDto findById(long id){

        Client client = clientRepository.findById(id)
                .orElseThrow(() ->
                        new ApiException(404, "Client introuvable"));

        return new ClientDto(
                client.getId(),
                client.getNom(),
                client.getEmail()
        );
    }

    public ClientDto update(Long id, CreateClientDto dto){

        Client client = clientRepository.findById(id)
                .orElseThrow(() ->
                        new ApiException(404, "Client introuvable"));

        client.setNom(dto.nom());
        client.setEmail(dto.email());

        client = clientRepository.save(client);

        return new ClientDto(
                client.getId(),
                client.getNom(),
                client.getEmail()
        );
    }

    public void delete(Long id){

        Client client = clientRepository.findById(id)
                .orElseThrow(() ->
                        new ApiException(404, "Client introuvable"));

        clientRepository.delete(client);
    }
}
