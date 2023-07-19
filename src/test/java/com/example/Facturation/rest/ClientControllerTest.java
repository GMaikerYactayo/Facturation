package com.example.Facturation.rest;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.example.Facturation.domain.Client;
import com.example.Facturation.service.ClientService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ClientControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ClientService clientService;

    @InjectMocks
    private ClientController clientController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(clientController).build();
    }

    @Test
    void findAll() throws Exception {
        //gicen
        Client client = new Client();
        client.setName("nome");
        client.setLastname("apellido");
        client.setEmail("nome@gmail.com");

        Client client2 = new Client();
        client2.setName("nome");
        client2.setLastname("apellido");
        client2.setEmail("nome@gmail.com");

        List<Client> clients = Arrays.asList(client, client2);
        given(clientService.findAll()).willReturn(clients);

        //when
        ResultActions response = mockMvc.perform(get("/api/v1/clients"));

        //then
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(clients.size()));
    }

    @Test
    void create() throws Exception {
        //given
        Client client = new Client();
        client.setName("nome");
        client.setLastname("apellido");
        client.setEmail("nome@gmail.com");

        given(clientService.save(any(Client.class))).willReturn(client);

        //when
        ResultActions response = mockMvc.perform(post("/api/v1/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(client)));

        //then
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(client.getName()))
                .andExpect(jsonPath("$.lastname").value(client.getLastname()))
                .andExpect(jsonPath("$.email").value(client.getEmail()));
    }
}