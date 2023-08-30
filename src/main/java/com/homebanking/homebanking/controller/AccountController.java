package com.homebanking.homebanking.controller;

import com.homebanking.homebanking.dtos.AccountDTO;
import com.homebanking.homebanking.models.Account;
import com.homebanking.homebanking.models.Client;
import com.homebanking.homebanking.repositories.AccountRepository;
import com.homebanking.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("api")
public class AccountController {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ClientRepository clientRepository;

    @GetMapping("/accounts")
    public List<AccountDTO> getClients() {
        return accountRepository.findAll().stream().map(AccountDTO::new).collect(toList());
    }

    @GetMapping("/accounts/{id}")
    public ResponseEntity<Object> getClient(@PathVariable Long id, Authentication authentication) {
        Client client = clientRepository.findByEmail(authentication.getName());
        Account account = accountRepository.findById(id).orElse(null);
        if (client== null){
            return new ResponseEntity<>("Client not found", HttpStatus.FORBIDDEN);
        }
        if( account == null){
            return new ResponseEntity<>("Account not found", HttpStatus.FORBIDDEN);
        }
        if (account.getClient().getId().equals(client.getId())) {
            return new ResponseEntity<>(new AccountDTO(account), HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>("You're  not the owner", HttpStatus.FORBIDDEN);
    }
    @PostMapping("/clients/current/accounts")
    public ResponseEntity<Object> createAccount(Authentication authentication) {
        //get client information
        Client client = clientRepository.findByEmail(authentication.getName());
        //max of accounts
        if (client.getAccounts().size() >= 3) {
            return new ResponseEntity<>("User has 3 accounts", HttpStatus.FORBIDDEN);
        }
        String accountNumber = createNumberAccount();
        Account newAccount = new Account(accountNumber, LocalDateTime.now(), 0.0);
        client.addAccount(newAccount);
        accountRepository.save(newAccount);
        clientRepository.save(client);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    protected String createNumberAccount() {
        String accountNumber;
        do {
            int random = (int) (Math.random() * 99999999);
            accountNumber = "VIN-" + random;
        } while (accountRepository.existsByNumber(accountNumber));
        return accountNumber;
    }

    @GetMapping("/clients/current/accounts")
    public List<AccountDTO> getClientAccount(Authentication authentication) {
        return clientRepository.findByEmail(authentication.getName()).getAccounts().stream().map(AccountDTO::new).collect(toList());
    }
}
