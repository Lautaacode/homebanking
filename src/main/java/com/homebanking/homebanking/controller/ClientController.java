package com.homebanking.homebanking.controller;

import com.homebanking.homebanking.models.Client;
import com.homebanking.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Optional;


import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("api")
public class ClientController {
    @Autowired
    private ClientRepository clientRepository;

    @RequestMapping("/clients")
    public List<Client> getClients() {
        return clientRepository.findAll();
    }

    @RequestMapping("/clients/{id}")
    public Optional<Client> getClient(@PathVariable("id") Long id){
        return clientRepository.findById(id);
    }

}
