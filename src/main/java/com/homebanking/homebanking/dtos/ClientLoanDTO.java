package com.homebanking.homebanking.dtos;

import com.homebanking.homebanking.models.ClientLoan;

public class ClientLoanDTO {
    private long id;
    private String name;
    private double amount;
    private int payments;
    private long loan_id;
    private String loan_name;

    public ClientLoanDTO(ClientLoan clientLoan) {
        this.id = clientLoan.getId();
        this.name = clientLoan.getLoan().getName();
        this.amount = clientLoan.getAmount();
        this.payments = clientLoan.getPayment();
        this.loan_id = clientLoan.getLoan().getId();
        this.loan_name = clientLoan.getLoan().getName();
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getAmount() {
        return amount;
    }

    public int getPayments() {
        return payments;
    }

    public long getLoan_id() {
        return loan_id;
    }

    public String getLoan_name() {
        return loan_name;
    }
}
