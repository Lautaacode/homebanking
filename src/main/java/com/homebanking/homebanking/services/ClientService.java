package com.homebanking.homebanking.services;

import com.homebanking.homebanking.dtos.ClientDTO;
import com.homebanking.homebanking.models.Client;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface ClientService {
    List<ClientDTO> getClients();
    void saveClient(Client client);
    Client findById(long id);
    ClientDTO getClient(long id);

    Client findByEmail(String email);

    ClientDTO getClientCurrent(String email);

}
