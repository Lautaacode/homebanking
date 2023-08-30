package com.homebanking.homebanking.controller;

import com.homebanking.homebanking.dtos.ClientDTO;
import com.homebanking.homebanking.models.Account;
import com.homebanking.homebanking.models.Client;
import com.homebanking.homebanking.repositories.AccountRepository;
import com.homebanking.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

import static java.util.stream.Collectors.toList;


@RestController
@RequestMapping("api")
public class ClientController {
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AccountController accountController;
    @Autowired
    private AccountRepository accountRepository;

    @GetMapping("/clients")
    public List<ClientDTO> getClients() {
        return clientRepository.findAll().stream().map(ClientDTO::new).collect(toList());
    }
    @GetMapping("/clients/{id}")
    public ClientDTO getClient(@PathVariable Long id){
        return clientRepository.findById(id).map(ClientDTO::new).orElse(null);
    }

    @GetMapping("/clients/current")
    public ClientDTO getClientCurrent(Authentication authentication){
        return new ClientDTO(clientRepository.findByEmail(authentication.getName()));
    }
    @PostMapping("/clients")
    public ResponseEntity<Object> registerClient(
            @RequestParam String firstName, @RequestParam String lastName,
            @RequestParam String email, @RequestParam String password) {
        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty()) {
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }
        if (clientRepository.findByEmail(email) != null) {
            return new ResponseEntity<>("Name already in use", HttpStatus.FORBIDDEN);
        }

        String accountNumber = accountController.createNumberAccount();
        //create new account
        Account account = new Account(accountNumber, LocalDateTime.now(),0.0);
        //save account
        accountRepository.save(account);
        //create new client
        Client client = new Client(firstName, lastName, email, passwordEncoder.encode(password));
        client.addAccount(account);
        //save client
        clientRepository.save(client);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
