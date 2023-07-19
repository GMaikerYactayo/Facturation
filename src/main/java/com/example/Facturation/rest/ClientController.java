package com.example.Facturation.rest;

import com.example.Facturation.domain.Client;
import com.example.Facturation.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class ClientController {

    private final ClientService clientService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/clients")
    public List<Client> findAll() {
        return clientService.findAll();
    }

    @PostMapping("/clients")
    public ResponseEntity<Client> create(@RequestBody Client client) {
        return ResponseEntity.ok(clientService.save(client));
    }

}
