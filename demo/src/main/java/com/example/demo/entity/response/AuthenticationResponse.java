package com.example.demo.entity.response;

import com.example.demo.enums.RoleEnum;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public class AuthenticationResponse {
    public long id ;
    public String fullname ;
    public String email;
    @Enumerated(value = EnumType.STRING)
    public RoleEnum roleEnum;
    public String token;
    public String username;

    public AuthenticationResponse(long id, String fullname, String email, RoleEnum roleEnum, String token, String username) {
        this.id = id;
        this.fullname = fullname;
        this.email = email;
        this.roleEnum = roleEnum;
        this.token = token;
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public AuthenticationResponse(){

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public RoleEnum getRoleEnum() {
        return roleEnum;
    }

    public void setRoleEnum(RoleEnum roleEnum) {
        this.roleEnum = roleEnum;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
