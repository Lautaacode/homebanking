package com.homebanking.homebanking.controller;

import com.homebanking.homebanking.dtos.AccountDTO;
import com.homebanking.homebanking.models.Account;
import com.homebanking.homebanking.models.Client;
import com.homebanking.homebanking.repositories.AccountRepository;
import com.homebanking.homebanking.repositories.ClientRepository;
import com.homebanking.homebanking.services.AccountService;
import com.homebanking.homebanking.services.ClientService;
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
    private AccountService accountService;

    @Autowired
    private ClientService clientService;

    @GetMapping("/accounts")
    public List<AccountDTO> getAccounts() {
        return accountService.getAccounts();
    }

    @GetMapping("/accounts/{id}")
    public ResponseEntity<Object> getAccount(@PathVariable Long id, Authentication authentication) {
        Client client = clientService.findByEmail(authentication.getName());
        Account account = accountService.findById(id);
        if (client == null) {
            return new ResponseEntity<>("Client not found", HttpStatus.FORBIDDEN);
        }
        if (account == null) {
            return new ResponseEntity<>("Account not found", HttpStatus.FORBIDDEN);
        }
        if (account.getClient().getId().equals(client.getId())) {
            return new ResponseEntity<>(new AccountDTO(account), HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>("You're  not the owner", HttpStatus.FORBIDDEN);
    }

    @PostMapping("/clients/current/accounts")
    public ResponseEntity<Object> createAccount(Authentication authentication) {
        //validate CLIENT
        boolean hasClientAuthority = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(authority -> authority.equals("CLIENT"));
        if (!hasClientAuthority) {
            return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
        }
        //get client information
        Client client = clientService.findByEmail(authentication.getName());
        //max of accounts
        if (client.getAccounts().size() >= 3) {
            return new ResponseEntity<>("User has 3 accounts", HttpStatus.FORBIDDEN);
        }

        Account newAccount = accountService.createAccount();
        client.addAccount(newAccount);
        accountService.saveAccount(newAccount);
        clientService.saveClient(client);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @GetMapping("/clients/current/accounts")
    public List<AccountDTO> getClientAccount(Authentication authentication) {
        //get client
        Client client = clientService.findByEmail(authentication.getName());
        //show accounts
        return client.getAccounts().stream().map(AccountDTO::new).collect(toList());
    }
}
