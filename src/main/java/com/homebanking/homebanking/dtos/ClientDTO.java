package com.homebanking.homebanking.dtos;

import com.homebanking.homebanking.models.Client;

public class ClientDTO {

    private Long id ;
    private String firstName;
    private String lastName;
    private String email;

    public ClientDTO() {
    }
    public ClientDTO(Client client) {
        this.id = client.getId();
        this.firstName = client.getFirstName();
        this.lastName = client.getLastName();
        this.email = client.getEmail();
    }
}
