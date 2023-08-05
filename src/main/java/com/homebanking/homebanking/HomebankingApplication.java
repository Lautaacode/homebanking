package com.homebanking.homebanking;

import com.homebanking.homebanking.models.Account;
import com.homebanking.homebanking.models.Client;
import com.homebanking.homebanking.repositories.ClientRepository;
import com.homebanking.homebanking.repositories.AccountRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@SpringBootApplication
public class HomebankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData(ClientRepository clientRepository, AccountRepository accountRepository){
		return (args -> {
			Client client_1 = new Client("Melba","Morel","melba@mindhub.com");
			clientRepository.save(client_1);
			Account account_1 = new Account("VIN001", LocalDateTime.now(),5000 );
			Account account_2 = new Account("VIN002", LocalDateTime.now(),7500 );
			LocalDateTime today =  LocalDateTime.now();
			LocalDateTime tomorrow = today.plusDays(1);
			account_2.setCreationDate(tomorrow);

			Client client_2 = new Client("Lautaro","Blanco","Lautaronicoblanco@hotmail.com");
			clientRepository.save(client_2);

			client_1.addAccount(account_1);
			client_1.addAccount(account_2);
			accountRepository.save(account_1);
			accountRepository.save(account_2);

		});
	}
}
