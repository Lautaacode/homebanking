package com.homebanking.homebanking.repositories;

import com.homebanking.homebanking.enums.CardColor;
import com.homebanking.homebanking.enums.CardType;
import com.homebanking.homebanking.models.Card;

import com.homebanking.homebanking.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;


@RepositoryRestResource
public interface CardRepository extends JpaRepository<Card, Long> {
    boolean existsByTypeAndColorAndClient (CardType type, CardColor color, Client client);
}
