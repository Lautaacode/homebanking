package com.homebanking.homebanking.services;

import com.homebanking.homebanking.dtos.CardDTO;
import com.homebanking.homebanking.enums.CardColor;
import com.homebanking.homebanking.enums.CardType;
import com.homebanking.homebanking.models.Card;
import com.homebanking.homebanking.models.Client;

import java.util.List;

public interface CardService {
    List<CardDTO> getAllCards();
    void saveCard(Card card);
    Card findById(long id);
    boolean existsByTypeAndColorAndClient( CardType cardType,CardColor cardColor,Client client);
    Card createCard(String cardHolder, CardType cardType, CardColor cardColor);
    int generateCvv();
    String generateNumber();
}
