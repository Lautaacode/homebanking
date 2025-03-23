package com.homebanking.homebanking.dtos;

import com.homebanking.homebanking.models.Account;

import java.time.LocalDateTime;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

public class AccountDTO {
    private Long id;
    private String number;
    private LocalDateTime creationDate;
    private double balance;
    private Set<TransactionDTO> transactions;

    public AccountDTO() {

    }

    public AccountDTO(Account account) {
        this.id = account.getId();
        this.number = account.getNumber();
        this.creationDate = account.getCreationDate();
        this.balance = account.getBalance();
        this.transactions = account.getTransactions().stream().map(TransactionDTO::new).collect(toSet());
    }

    public Long getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public double getBalance() {
        return balance;
    }

    public Set<TransactionDTO> getTransactions() {
        return transactions;
    }
}
