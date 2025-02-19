package com.example.demo.Service;

import com.example.demo.entity.Account;
import com.example.demo.repository.AuthenticationRepo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class TokenService {

    @Autowired
    AuthenticationRepo authenticationRepo;


    private final String SECRET_KEY = "HT4bb6d1dfbafb64a681139d1586b6f1160d18159afd57c8c79136d7490630407c";
    private SecretKey getSiginKey() {
        byte [] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);

            }

// tạo token
    public String generateToken(Account account) {
        String token = Jwts.builder()
                .subject(account.getId()+"").issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 *60 *24)).
                signWith(getSiginKey()).
                compact();
        return token;
    }
// verify token
    public Account getStudentByToken(String token) {
        Claims claims = Jwts.parser().
                verifyWith(getSiginKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        String idString = claims.getSubject();
        long id = Long.parseLong(idString);
        Account account = authenticationRepo.findById(id);
        return account;
    }


}
