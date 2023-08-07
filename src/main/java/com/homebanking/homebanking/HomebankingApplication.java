package com.homebanking.homebanking;

import com.homebanking.homebanking.models.Account;
import com.homebanking.homebanking.models.Client;
import com.homebanking.homebanking.repositories.ClientRepository;
import com.homebanking.homebanking.repositories.AccountRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;

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

			Account account_1 = new Account(client_1,"VIN001", LocalDateTime.now(),5000 );
			Account account_2 = new Account(client_1,"VIN002", LocalDateTime.now().plusDays(1),7500 );
			accountRepository.save(account_1);
			accountRepository.save(account_2);

			Client client_2 = new Client("Lautaro","Blanco","Lautaronicoblanco@hotmail.com");
			clientRepository.save(client_2);


		});
	}
}
