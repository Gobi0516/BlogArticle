package com.gobi.blog.services.impl;

import com.gobi.blog.domain.entities.User;
import com.gobi.blog.repositories.UserRepository;
import com.gobi.blog.security.BlogUserDetails;
import com.gobi.blog.services.AuthService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserDetailsService userDetailsService;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // Minimum 32 characters for HS256
    private static final String SECRET_KEY =
            "mySuperSecretKeyForJwtToken123456";
    private static final long EXPIRATION_MS = 3600 * 1000L; // 1 hour

    @Override
    public UserDetails authenticate(String email, String password) {
        UserDetails user = userDetailsService.loadUserByUsername(email);
        authenticationManager.authenticate(
                new org.springframework.security.authentication.UsernamePasswordAuthenticationToken(email, password));
        return user;
    }

    @Override
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>(); //  Fixed type (was Maps<String,object>)

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_MS))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256) //  Use Key object, not plain string
                .compact();
    }

    @Override
    public UserDetails validateToken(String token) {
        String username = extractUsername(token);
        return userDetailsService.loadUserByUsername(username);
    }

    @Override
    public UserDetails register(String email, String password, String name) {
        String trimmedEmail = email.trim().toLowerCase();
        if (userRepository.findByEmail(trimmedEmail).isPresent()) {
            throw new IllegalArgumentException("User with email " + email + " already exists.");
        }

        User user = User.builder()
                .email(trimmedEmail)
                .password(passwordEncoder.encode(password))
                .name(name)
                .build();

        User savedUser = userRepository.save(user);
        return new BlogUserDetails(savedUser);
    }

    private String extractUsername(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(
                SECRET_KEY.getBytes(StandardCharsets.UTF_8)
        );
    }


}