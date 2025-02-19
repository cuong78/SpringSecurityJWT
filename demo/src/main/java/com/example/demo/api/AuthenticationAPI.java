package com.example.demo.api;

import com.example.demo.Service.AuthenticationService;
import com.example.demo.Service.EmailService;
import com.example.demo.entity.Account;
import com.example.demo.entity.request.AccountRequest;
import com.example.demo.entity.request.AuthenticationRequest;
import com.example.demo.entity.request.ForgotPasswordRequest;
import com.example.demo.entity.request.ResetPasswordRequest;
import com.example.demo.entity.response.AuthenticationResponse;
import com.example.demo.repository.AuthenticationRepo;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api")
public class AuthenticationAPI {

    @Autowired
    AuthenticationService authenticationService;
    @Autowired
    AuthenticationRepo authenticationRepo;

    @Autowired
    EmailService emailService;

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



    @PostMapping("forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody ForgotPasswordRequest request) {
        try {
            // Tìm tài khoản bằng email
            Account account = authenticationRepo.findByEmail(request.getEmail())
                    .orElseThrow(() -> new UsernameNotFoundException("Email không tồn tại"));

            // Tạo token
            String token = UUID.randomUUID().toString();
            authenticationService.createPasswordResetTokenForAccount(account, token);

            // Gửi token qua email
            String emailSubject = "Reset Password Token";
            String emailText = "Your reset password token is: " + token;
            emailService.sendEmail(request.getEmail(), emailSubject, emailText);

            // Trả về thông báo thành công
            return ResponseEntity.ok("Reset token has been sent to your email.");
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("reset-password")
    public ResponseEntity<?> resetPassword(
            @RequestParam("token") String token,
            @RequestBody ResetPasswordRequest request
    ) {
        try {
            Account account = authenticationService.validatePasswordResetToken(token);
            authenticationService.changePassword(account, request.getNewPassword());
            authenticationService.deleteResetToken(token);
            return ResponseEntity.ok("Đặt lại mật khẩu thành công");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }



}
