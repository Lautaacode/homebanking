package com.homebanking.homebanking.services;

import com.homebanking.homebanking.dtos.ClientLoanDTO;
import com.homebanking.homebanking.models.ClientLoan;

import java.util.List;


public interface ClientLoanService {
    List<ClientLoanDTO> getClientLoans();
    ClientLoan findById(long id);
    void saveClientLoan(ClientLoan clientLoan);
    ClientLoanDTO getClientLoan(long id);
    ClientLoan createClientLoan(double amount,int payments);
}
