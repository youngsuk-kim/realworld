package com.example.realworld.service;

import com.example.realworld.controller.dto.TokenDto;
import com.example.realworld.entity.RefreshToken;
import com.example.realworld.repository.RefreshTokenRepository;
import com.example.realworld.security.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenService {
    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    private final AuthenticationProvider authenticationProvider;

    public RefreshToken createToken(Authentication auth) {
        Authentication authentication = authenticationProvider.authenticate(auth);
        TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);

        return saveToken(authentication.getName(), tokenDto.getAccessToken());
    }

    private RefreshToken saveToken(String authName, String refreshToken) {
        RefreshToken saveRefreshToken = RefreshToken.builder()
                .key(authName)
                .value(refreshToken)
                .build();

        refreshTokenRepository.save(saveRefreshToken);

        return saveRefreshToken;
    }
}
