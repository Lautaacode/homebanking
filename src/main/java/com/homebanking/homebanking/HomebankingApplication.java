package com.homebanking.homebanking;

import com.homebanking.homebanking.models.Client;
import com.homebanking.homebanking.repositories.ClientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Scanner;

@SpringBootApplication
public class HomebankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData(ClientRepository clientRepository){
		return (args -> {
			Client client = new Client("Melba","Morel","melba@mindhub.com");
			clientRepository.save(client);
		});
	}
}
