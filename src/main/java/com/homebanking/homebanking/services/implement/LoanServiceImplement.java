package com.homebanking.homebanking.services.implement;

import com.homebanking.homebanking.dtos.LoanDTO;

import com.homebanking.homebanking.models.Loan;
import com.homebanking.homebanking.repositories.LoanRepository;
import com.homebanking.homebanking.services.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LoanServiceImplement implements LoanService {

    @Autowired
    private LoanRepository loanRepository;
    @Override
    public List<LoanDTO> getLoans() {
        return loanRepository.findAll().stream().map(LoanDTO::new).collect(Collectors.toList());
    }

    @Override
    public Loan findById(long id) {
        return loanRepository.findById(id).orElse(null);
    }

    @Override
    public void saveLoans(Loan loan) {
        loanRepository.save(loan);
    }

    @Override
    public LoanDTO getLoan(long id) {
        return new LoanDTO(this.findById(id));
    }
}
