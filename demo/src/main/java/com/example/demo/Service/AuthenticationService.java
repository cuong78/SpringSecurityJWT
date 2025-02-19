package com.example.demo.Service;

import com.example.demo.entity.Account;
import com.example.demo.entity.PasswordResetToken;
import com.example.demo.entity.request.AccountRequest;
import com.example.demo.entity.request.AuthenticationRequest;
import com.example.demo.entity.response.AuthenticationResponse;
import com.example.demo.enums.RoleEnum;
import com.example.demo.repository.AuthenticationRepo;
import com.example.demo.repository.PasswordResetTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

@Service
public class AuthenticationService implements UserDetailsService {
    @Autowired
    AuthenticationRepo authenticationRepo;


    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    TokenService tokenService;

    public Account register(AccountRequest accountRequest) {
        // xử lí logic
// String currentPassword = account.getPassword();
// String newPassword = passwordEncoder.encode(currentPassword);
// account.setPassword(newPassword);
// lưu xuống database




        Account account = new Account();

        account.setUsername(accountRequest.getUsername());
        account.setRoleEnum(RoleEnum.CUSTOMER);
        account.setPassword(passwordEncoder.encode(accountRequest.getPassword()));
        account.setFullname(accountRequest.getFullname());
        account.setEmail(accountRequest.getEmail());
        Account newAccount = authenticationRepo.save(account);
        return newAccount;

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return authenticationRepo.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("account not found"));

    }

    public AuthenticationResponse login(AuthenticationRequest authenticationRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),
                            authenticationRequest.getPassword()
                    )
            );


        } catch (Exception e) {
            throw new NullPointerException("Wrong username or password");
        }

        Account account = authenticationRepo.findByUsername(authenticationRequest.getUsername()).orElseThrow();
        String token = tokenService.generateToken(account);
        AuthenticationResponse authenticationResponse = new AuthenticationResponse();
        authenticationResponse.setToken(token);
        authenticationResponse.setEmail(account.getEmail());
        authenticationResponse.setFullname(account.getFullname());
        authenticationResponse.setRoleEnum(account.getRoleEnum());
        authenticationResponse.setId(account.getId());
        authenticationResponse.setUsername(account.getUsername());

        return authenticationResponse;
    }


    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;



    private Date calculateExpiryDate(int expiryTimeInSeconds) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.SECOND, expiryTimeInSeconds);
        return new Date(cal.getTime().getTime());
    }

    public Account validatePasswordResetToken(String token) {
        PasswordResetToken passToken = passwordResetTokenRepository.findByToken(token);

        if (passToken.getExpiryDate().before(new Date())) {
            throw new IllegalArgumentException("Token expired");
        }
        return passToken.getAccount();
    }

    public void changePassword(Account account, String newPassword) {
        account.setPassword(passwordEncoder.encode(newPassword));
        authenticationRepo.save(account);
    }

    public void deleteResetToken(String token) {
        PasswordResetToken resetToken = passwordResetTokenRepository.findByToken(token);

        passwordResetTokenRepository.delete(resetToken);
    }


    @Autowired
    private EmailService emailService;

    public void createPasswordResetTokenForAccount(Account account, String token) {
        PasswordResetToken resetToken = new PasswordResetToken();
        resetToken.setToken(token);
        resetToken.setAccount(account);
        resetToken.setExpiryDate(calculateExpiryDate(60 * 60)); // 1 giờ
        passwordResetTokenRepository.save(resetToken);
    }


}