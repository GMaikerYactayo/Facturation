package com.example.Facturation.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import com.example.Facturation.domain.Client;
import com.example.Facturation.repository.ClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
class ClientServiceImplTest {

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private ClientServiceImpl clientService;

    private Client client;

    @BeforeEach
    void setUp() {
        client = new Client();
        client.setName("John");
        client.setLastname("Arias");
        client.setEmail("john@example.com");
    }

    @DisplayName("Find all clients")
    @Test
    void findAll() {
        //give
        Client client2 = new Client();
        client2.setName("John");
        client2.setLastname("Arias");
        client2.setEmail("john@example.com");
        given(clientRepository.findAll()).willReturn(List.of(client2, client));

        //when
        List<Client> clients = clientService.findAll();

        //then
        assertThat(clients).isNotNull();
        assertThat(clients.size()).isEqualTo(2);
    }

    @DisplayName("Save client")
    @Test
    void save() {
        //give
        given(clientRepository.save(client)).willReturn(client);

        //when
        Client savedClient = clientService.save(client);

        //then
        assertThat(savedClient).isEqualTo(client);
    }
}