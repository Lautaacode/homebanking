package com.homebanking.homebanking.controller;

import com.homebanking.homebanking.dtos.CardDTO;
import com.homebanking.homebanking.enums.CardColor;
import com.homebanking.homebanking.enums.CardType;
import com.homebanking.homebanking.models.Card;
import com.homebanking.homebanking.models.Client;
import com.homebanking.homebanking.repositories.CardRepository;
import com.homebanking.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api")
public class CardController {
    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private ClientRepository clientRepository;

    @GetMapping("/cards")
    public List<CardDTO> getAllCards() {
        return cardRepository.findAll().stream().map(CardDTO::new).collect(toList());
    }

    @GetMapping("/clients/current/cards")
    public List<CardDTO> getCurrentClientCards(Authentication authentication) {
        return clientRepository.findByEmail(authentication.getName()).getCards().stream().map(CardDTO::new).collect(toList());
    }

    @PostMapping("/clients/current/cards")
    public ResponseEntity<Object> createCurrentCard(
            @RequestParam CardType cardType, @RequestParam CardColor cardColor, Authentication authentication) {
        //get client information
        Client client = clientRepository.findByEmail(authentication.getName());

        //validation card
        if (cardRepository.existsByTypeAndColorAndClient(cardType, cardColor, client)) {
            return new ResponseEntity<>("card alredy exist", HttpStatus.FORBIDDEN);
        }

        //create card
        String cardHolder = client.getFirstName() + " " + client.getLastName();
        Card newCard = new Card(cardHolder, cardType, cardColor,
                generateNumber(), generateCvv(), LocalDateTime.now(), LocalDateTime.now().plusYears(5));
        newCard.setClient(client);
        cardRepository.save(newCard);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    private int generateCvv() {
        return (int) (Math.random() * 999);
    }

    private String generateNumber() {
        DecimalFormat format = new DecimalFormat("0000");
        String number = "";
        for (int i = 0; i < 4; i++) {
            number += format.format((int) (Math.random() * 9999));
            if (i != 3) {
                number += "-";
            }
        }
        return number;
    }

}
