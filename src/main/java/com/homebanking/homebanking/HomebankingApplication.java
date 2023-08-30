package com.homebanking.homebanking;

import com.homebanking.homebanking.enums.CardColor;
import com.homebanking.homebanking.enums.CardType;
import com.homebanking.homebanking.models.*;
import com.homebanking.homebanking.enums.TransactionType;
import com.homebanking.homebanking.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.net.Authenticator;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
public class HomebankingApplication {
    public static void main(String[] args) {
        SpringApplication.run(HomebankingApplication.class, args);
    }

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Bean
    public CommandLineRunner initData(ClientRepository clientRepository,
                                      AccountRepository accountRepository,
                                      TransactionRepository transactionRepository,
                                      LoanRepository loanRepository,
                                      ClientLoanRepository clientLoanRepository,
                                      CardRepository cardRepository) {
        return (args -> {
            Client client_1 = new Client("Melba", "Morel", "melba@mindhub.com", passwordEncoder.encode("melba"));
            Client client_2 = new Client("Lautaro", "Blanco", "lautaronicoblanco@hotmail.com", passwordEncoder.encode("melba"));
            Client client_3 = new Client("Admin", "Admin", "admin@admin.com", passwordEncoder.encode("admin"));
            clientRepository.save(client_1);
            clientRepository.save(client_2);
            clientRepository.save(client_3);

            Account account_1 = new Account("VIN001", LocalDateTime.now(), 5000);
            Account account_2 = new Account("VIN002", LocalDateTime.now().plusDays(1), 7500);
            Account account_3 = new Account("VIN003", LocalDateTime.now().plusDays(1), 7500);
            accountRepository.save(account_1);
            accountRepository.save(account_2);
            accountRepository.save(account_3);

            client_1.addAccount(account_1);
            client_1.addAccount(account_2);
            client_2.addAccount(account_3);
            clientRepository.save(client_1);
            clientRepository.save(client_2);
            accountRepository.save(account_3);

            Transaction transaction_1 = new Transaction(TransactionType.DEBIT, 5000, "Car wash", LocalDateTime.now());
            Transaction transaction_2 = new Transaction(TransactionType.CREDIT, 5000, "Money", LocalDateTime.now());
            Transaction transaction_3 = new Transaction(TransactionType.CREDIT, 5000, "Money", LocalDateTime.now());

            account_1.addTransactions(transaction_1);
            account_2.addTransactions(transaction_2);
            account_3.addTransactions(transaction_3);
            accountRepository.save(account_1);
            accountRepository.save(account_2);
            accountRepository.save(account_3);
            transactionRepository.save(transaction_1);
            transactionRepository.save(transaction_2);
            transactionRepository.save(transaction_3);

            Loan loan_1 = new Loan("Mortgage", 500000, List.of(12,24,36,48,60));
            Loan loan_2 = new Loan("Personal", 500000, List.of(6,12,24));
            Loan loan_3 = new Loan("Automotive", 500000, List.of(12,24,36,48,60));

            loanRepository.save(loan_1);
            loanRepository.save(loan_2);
            loanRepository.save(loan_3);
            ClientLoan clientLoan_1 = new ClientLoan(400000,loan_1.getPayments().get(4));
            ClientLoan clientLoan_2 = new ClientLoan(50000,loan_2.getPayments().get(1));
            ClientLoan clientLoan_3 = new ClientLoan(100000,loan_2.getPayments().get(2));
            ClientLoan clientLoan_4 = new ClientLoan(200000,loan_3.getPayments().get(2));


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

            Card card_1 = new Card(client_1.getFirstName() + " " + client_1.getLastName(),CardType.DEBIT, CardColor.GOLD,"2316-5416-3854-2184",970,LocalDateTime.now(),LocalDateTime.now().plusYears(5));
            Card card_2 = new Card(client_1.getFirstName() + " " + client_1.getLastName(),CardType.CREDIT, CardColor.TITANIUM,"5423-8465-3214-5483",320,LocalDateTime.now(),LocalDateTime.now().plusYears(5));
            Card card_3 = new Card(client_1.getFirstName() + " " + client_1.getLastName(),CardType.DEBIT, CardColor.SILVER,"5456-1321-4541-8973",110,LocalDateTime.now(),LocalDateTime.now().plusYears(5));

            client_1.addCard(card_1);
            client_1.addCard(card_2);
            client_2.addCard(card_3);
            clientRepository.save(client_1);
            clientRepository.save(client_2);

            cardRepository.save(card_1);
            cardRepository.save(card_2);
            cardRepository.save(card_3);
        });
    }
}
