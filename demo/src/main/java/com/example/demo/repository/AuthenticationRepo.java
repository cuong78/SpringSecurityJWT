package com.example.demo.repository;

import com.example.demo.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface AuthenticationRepo extends JpaRepository<Account, Long> {
    Account findById(long id);

    Optional <Account> findByUsername(String username);

}

