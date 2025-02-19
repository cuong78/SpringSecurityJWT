package com.example.demo.entity.request;

public class AuthenticationRequest {

    public  String username;
    public  String password;

    public AuthenticationRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }
    public AuthenticationRequest(){}

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
