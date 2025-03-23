package com.homebanking.homebanking.services;

import com.homebanking.homebanking.dtos.LoanDTO;
import com.homebanking.homebanking.models.Loan;

import java.util.List;

public interface LoanService {
    List<LoanDTO> getLoans();
    Loan findById(long id);
    void saveLoans(Loan loan);
    LoanDTO getLoan(long id);

}
