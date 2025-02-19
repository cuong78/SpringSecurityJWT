package com.example.demo.api;

import com.example.demo.Service.AuthenticationService;
import com.example.demo.entity.Account;
import com.example.demo.entity.request.AccountRequest;
import com.example.demo.entity.request.AuthenticationRequest;
import com.example.demo.entity.response.AuthenticationResponse;
import com.example.demo.repository.AuthenticationRepo;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
public class AuthenticationAPI {

    @Autowired
    AuthenticationService authenticationService;

    @PostMapping ("register")
    public ResponseEntity register (@Valid @RequestBody AccountRequest account) {
        Account newAccount = authenticationService.register(account);
        return ResponseEntity.ok(newAccount);
    }


    @PostMapping ("login")
    public ResponseEntity login (@RequestBody AuthenticationRequest authenticationRequest) {
        AuthenticationResponse authenticationRepon= authenticationService.login(authenticationRequest);
        return ResponseEntity.ok(authenticationRepon);
    }



}
