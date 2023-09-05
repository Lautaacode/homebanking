package com.homebanking.homebanking.controller;

import com.homebanking.homebanking.dtos.CardDTO;
import com.homebanking.homebanking.dtos.ClientDTO;
import com.homebanking.homebanking.enums.CardColor;
import com.homebanking.homebanking.enums.CardType;
import com.homebanking.homebanking.models.Card;
import com.homebanking.homebanking.models.Client;

import com.homebanking.homebanking.services.CardService;
import com.homebanking.homebanking.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;


import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api")
public class CardController {
    @Autowired
    private ClientService clientService;
    @Autowired
    private CardService cardService;

    @GetMapping("/cards")
    public List<CardDTO> getAllCards() {
        return cardService.getAllCards();
    }

    @GetMapping("/clients/current/cards")
    public List<CardDTO> getCurrentClientCards(Authentication authentication) {
        //get client
        Client currentClient= clientService.findByEmail(authentication.getName());
        //get cards
        return currentClient.getCards().stream().map(CardDTO::new).collect(toList());
    }

    @PostMapping("/clients/current/cards")
    public ResponseEntity<Object> createCard(
            @RequestParam CardType cardType, @RequestParam CardColor cardColor, Authentication authentication) {
        //validate CLIENT
        boolean hasClientAuthority = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(authority -> authority.equals("CLIENT"));
        if (!hasClientAuthority) {
            return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
        }
        //get client information
        Client client = clientService.findByEmail(authentication.getName());
        //validation card
        if (cardService.existsByTypeAndColorAndClient(cardType, cardColor, client)) {
            return new ResponseEntity<>("card alredy exist", HttpStatus.FORBIDDEN);
        }

        //create card
        Card newCard = cardService.createCard(client.cardHolder(),cardType,cardColor);
        client.addCard(newCard);
        cardService.saveCard(newCard);
        clientService.saveClient(client);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


}
