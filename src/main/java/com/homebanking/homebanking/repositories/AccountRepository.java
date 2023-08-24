package com.homebanking.homebanking.repositories;

import com.homebanking.homebanking.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface AccountRepository extends JpaRepository<Account,Long> {
    List<Account> findByClientEmail(String email);

    boolean existsByNumber(String number);
}
