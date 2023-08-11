package com.homebanking.homebanking;

import com.homebanking.homebanking.models.*;
import com.homebanking.homebanking.enums.TransactionType;
import com.homebanking.homebanking.repositories.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
public class HomebankingApplication {

    public static void main(String[] args) {
        SpringApplication.run(HomebankingApplication.class, args);
    }

    @Bean
    public CommandLineRunner initData(ClientRepository clientRepository,
                                      AccountRepository accountRepository,
                                      TransactionRepository transactionRepository,
                                      LoanRepository loanRepository,
                                      ClientLoanRepository clientLoanRepository) {
        return (args -> {
            Client client_1 = new Client("Melba", "Morel", "melba@mindhub.com");
            Client client_2 = new Client("Lautaro", "Blanco", "Lautaronicoblanco@hotmail.com");
            clientRepository.save(client_1);
            clientRepository.save(client_2);

            Account account_1 = new Account("VIN001", LocalDateTime.now(), 5000);
            Account account_2 = new Account("VIN002", LocalDateTime.now().plusDays(1), 7500);
            accountRepository.save(account_1);
            accountRepository.save(account_2);

            client_1.addAccount(account_1);
            client_1.addAccount(account_2);
            clientRepository.save(client_1);

            Transaction transaction_1 = new Transaction(TransactionType.DEBIT, 5000, "Car wash", LocalDateTime.now());
            Transaction transaction_2 = new Transaction(TransactionType.CREDIT, 5000, "Money", LocalDateTime.now());

            account_1.addTransactions(transaction_1);
            account_2.addTransactions(transaction_2);
            accountRepository.save(account_1);
            accountRepository.save(account_2);
            transactionRepository.save(transaction_1);
            transactionRepository.save(transaction_2);

            Loan loan_1 = new Loan("Mortgage", 500000, List.of(12,24,36,48,60));
            Loan loan_2 = new Loan("Personal", 500000, List.of(6,12,24));
            Loan loan_3 = new Loan("Automotive", 500000, List.of(12,24,36,48,60));

            loanRepository.save(loan_1);
            loanRepository.save(loan_2);
            loanRepository.save(loan_3);
            ClientLoan clientLoan_1 = new ClientLoan(loan_1.getName(),400000,loan_1.getPayments().get(4));
            ClientLoan clientLoan_2 = new ClientLoan(loan_2.getName(),50000,loan_2.getPayments().get(1));
            ClientLoan clientLoan_3 = new ClientLoan(loan_2.getName(),100000,loan_2.getPayments().get(2));
            ClientLoan clientLoan_4 = new ClientLoan(loan_2.getName(),200000,loan_3.getPayments().get(2));


            loan_1.addClientLoan(clientLoan_1);
            loan_2.addClientLoan(clientLoan_2);
            loan_2.addClientLoan(clientLoan_3);
            loan_3.addClientLoan(clientLoan_4);
            loanRepository.save(loan_1);
            loanRepository.save(loan_2);
            loanRepository.save(loan_3);


            client_1.addClientLoan(clientLoan_1);
            client_1.addClientLoan(clientLoan_2);
            client_2.addClientLoan(clientLoan_3);
            client_2.addClientLoan(clientLoan_4);
            clientRepository.save(client_1);
            clientRepository.save(client_2);


            clientLoanRepository.save(clientLoan_1);
            clientLoanRepository.save(clientLoan_2);
            clientLoanRepository.save(clientLoan_3);
            clientLoanRepository.save(clientLoan_4);
        });
    }
}
