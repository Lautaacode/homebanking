package com.homebanking.homebanking.controller;

import com.homebanking.homebanking.dtos.CardDTO;
import com.homebanking.homebanking.enums.CardColor;
import com.homebanking.homebanking.enums.CardType;
import com.homebanking.homebanking.models.Card;
import com.homebanking.homebanking.repositories.CardRepository;
import com.homebanking.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api")
public class CardController {
    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private ClientRepository clientRepository;

    @RequestMapping("/cards")
    public List<CardDTO> getAllCards(){
        return cardRepository.findAll().stream().map(CardDTO::new).collect(toList());
    }

    @RequestMapping("/clients/current/cards")
    public List<CardDTO> getCurrentClientCards(Authentication authentication){
        return cardRepository.findByClientEmail(authentication.getName()).stream().map(CardDTO::new).collect(toList());
    }

    @RequestMapping(path = "/clients/current/cards",method = RequestMethod.POST)
    public ResponseEntity<Object> createCurrentCard(@RequestParam String cardType, @RequestParam String cardColor, Authentication authentication){
        //validate
        if(cardType.isEmpty()) {
            return new ResponseEntity<>("Missing data: card type is empty", HttpStatus.FORBIDDEN);
        }
        if(cardColor.isEmpty()) {
            return new ResponseEntity<>("Missing data: card color is empty", HttpStatus.FORBIDDEN);
        }
        //max of 3 cards
        if(cardRepository.findByClientEmail(authentication.getName()).size()>=3){
            return new ResponseEntity<>("User has 3 cards", HttpStatus.FORBIDDEN);
        }
        //create card
        Card newCard=new Card(CardType.valueOf(cardType), CardColor.valueOf(cardColor),generateNumber(),generateCvv(), LocalDateTime.now(),LocalDateTime.now().plusYears(5));
        newCard.setClient(clientRepository.findByEmail(authentication.getName()));
        cardRepository.save(newCard);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    private int generateCvv(){
        return (int) (Math.random() * 999);
    }

    private String generateNumber(){
        DecimalFormat format=new DecimalFormat("0000");
        String number="";
        for(int i=0;i<4;i++){
            number += format.format((int)(Math.random() * 9999));
            if(i!=3){
                number+="-";
            }
        }
        return number;
    }

}
