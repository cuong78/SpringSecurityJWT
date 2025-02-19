package com.example.demo.entity.request;

public class AccountRequest {
    public String fullname ;
    public String password ;
    public String email;
    public String username;

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }


    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public AccountRequest(String fullname, String password, String email, String username) {
        this.fullname = fullname;
        this.password = password;
        this.email = email;
        this.username = username;
    }
    public AccountRequest(){}

}
