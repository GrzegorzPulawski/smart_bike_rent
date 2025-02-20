package com.smart_bike_rent.controller;

import com.smart_bike_rent.dto.ClientDTO;
import com.smart_bike_rent.request.CreateClientRequest;
import com.smart_bike_rent.service.ClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/clients")
public class ClientController {
    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }
    @PostMapping
    public void createClient(@RequestBody CreateClientRequest createClientRequest){
        log.info("Create client:"+createClientRequest);
        clientService.addClient(createClientRequest);
    }
    @GetMapping
    public List<ClientDTO> clientsList(){
        List<ClientDTO> clientList = clientService.findAllClients();
        log.info("List of clients has: " + clientList.size() + " positions");
        return clientService.findAllClients();
    }
    @GetMapping("/findByLastName")
    public ResponseEntity<List<ClientDTO>> getClientByLastName(@RequestParam("lastName") String lastName) {
        List<ClientDTO> clients = clientService.findByLastName(lastName);
        if (clients.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(clients);
        }
        return ResponseEntity.ok(clients);
    }
    @DeleteMapping("/delete")
    public void deleteClientById(@RequestParam("idClient") Long idClient){
        log.info("Delete client with ID: "+idClient);
        clientService.deleteClient(idClient);
    }

}
