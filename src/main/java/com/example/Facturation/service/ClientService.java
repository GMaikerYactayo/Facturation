package com.example.Facturation.service;

import com.example.Facturation.domain.Client;

import java.util.List;

public interface ClientService {

    List<Client> findAll();
    Client save(Client client);

}
