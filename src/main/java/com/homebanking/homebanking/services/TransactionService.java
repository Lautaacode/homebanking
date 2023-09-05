package com.homebanking.homebanking.services;

import com.homebanking.homebanking.dtos.TransactionDTO;
import com.homebanking.homebanking.models.Transaction;

import java.util.List;

public interface TransactionService {
    List<TransactionDTO> getTransactions();
    void saveTransaction(Transaction transaction);
    Transaction findById(long id);
    TransactionDTO getTransaction(long id);

    Transaction createDebitTransaction(double amount, String description);
    Transaction createCreditTransaction(double amount, String description);
}
