package com.gobi.blog.controllers;

import com.gobi.blog.dtos.AuthResponse;
import com.gobi.blog.dtos.LoginRequest;
import com.gobi.blog.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        UserDetails userDetails = authService.authenticate(request.getEmail(), request.getPassword());
        String token = authService.generateToken(userDetails);
        AuthResponse authResponse = AuthResponse.builder()
                .token(token)
                .expiresIn(3600)
                .build();
        return ResponseEntity.ok(authResponse);
    }
}
