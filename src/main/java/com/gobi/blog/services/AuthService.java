package com.gobi.blog.services;

import org.springframework.security.core.userdetails.UserDetails;


public interface AuthService {
    UserDetails authenticate(String email, String password);

    String generateToken(UserDetails userDetails);

    UserDetails validateToken(String token);

    UserDetails register(String email, String password, String name);
}
