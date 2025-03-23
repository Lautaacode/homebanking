package com.homebanking.homebanking.services.implement;

import com.homebanking.homebanking.dtos.ClientDTO;
import com.homebanking.homebanking.models.Client;
import com.homebanking.homebanking.repositories.ClientRepository;
import com.homebanking.homebanking.services.ClientService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class ClientServiceImplement implements ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public List<ClientDTO> getClients() {
        return clientRepository.findAll().stream().map(ClientDTO::new).collect(toList());
    }

    @Override
    public void saveClient(Client client) {
        clientRepository.save(client);
    }

    @Override
    public Client findById(long id) {
        return clientRepository.findById(id).orElse(null);
    }

    @Override
    public ClientDTO getClient(long id) {
        return new ClientDTO(this.findById(id));
    }

    @Override
    public Client findByEmail(String email) {
        return clientRepository.findByEmail(email);
    }

    @Override
    public ClientDTO getClientCurrent(String email){
        return new ClientDTO(this.findByEmail(email));
    }

}
