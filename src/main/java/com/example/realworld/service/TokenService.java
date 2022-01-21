package com.example.realworld.service;

import com.example.realworld.controller.dto.TokenDto;
import com.example.realworld.security.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenService {
    private final TokenProvider tokenProvider;
    private final AuthenticationProvider authenticationProvider;

    public String createToken(Authentication auth) {
        Authentication authentication = authenticationProvider.authenticate(auth);
        return tokenProvider.generateAccessToken(authentication);
    }
}
