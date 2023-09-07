package com.homebanking.homebanking.controllers;

import com.homebanking.homebanking.dtos.ClientDTO;
import com.homebanking.homebanking.models.Account;
import com.homebanking.homebanking.models.Client;
import com.homebanking.homebanking.services.AccountService;
import com.homebanking.homebanking.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;



@RestController
@RequestMapping("api")
public class ClientController {
    @Autowired
    private ClientService clientService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @GetMapping("/clients")
    public List<ClientDTO> getClients() {
        return clientService.getClients();
    }
    @GetMapping("/clients/{id}")
    public ClientDTO getClient(@PathVariable Long id){
        return clientService.getClient(id);
    }

    @GetMapping("/clients/current")
    public ClientDTO getClientCurrent(Authentication authentication){
        return clientService.getClientCurrent(authentication.getName());
    }
    @PostMapping("/clients")
    public ResponseEntity<Object> registerClient(
            @RequestParam String firstName, @RequestParam String lastName,
            @RequestParam String email, @RequestParam String password) {
        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty()) {
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }
        if (clientService.findByEmail(email) != null) {
            return new ResponseEntity<>("Name already in use", HttpStatus.FORBIDDEN);
        }

        //create new account
        Account account = accountService.createAccount();
        //save account
        accountService.saveAccount(account);
        //create new client
        Client client = new Client(firstName, lastName, email, passwordEncoder.encode(password));
        client.addAccount(account);
        //save client
        clientService.saveClient(client);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
