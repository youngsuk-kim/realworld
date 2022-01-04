package com.example.realworld.service;

import com.example.realworld.controller.dto.*;
import com.example.realworld.entity.User;
import com.example.realworld.entity.RefreshToken;
import com.example.realworld.exception.UserNotFoundException;
import com.example.realworld.repository.UserRepository;
import com.example.realworld.repository.RefreshTokenRepository;
import com.example.realworld.security.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;

import static com.example.realworld.security.SecurityUtil.resolveToken;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional(readOnly = true)
    public UserResponseDto getCurrentUser(Principal principal, String token) {
        User findUser = userRepository.findById(Long.valueOf(principal.getName()))
                .orElseThrow(() -> new UserNotFoundException(Long.valueOf(principal.getName())));

        return UserResponseDto.of(findUser, resolveToken(token));
    }

    public UserResponseDto signup(SignUpRequestDto signUpRequestDto) {
        if (userRepository.existsByEmail(signUpRequestDto.getEmail())) {
            throw new RuntimeException("이미 가입되어 있는 유저입니다");
        }

        User user = signUpRequestDto.toUser(passwordEncoder);

        return UserResponseDto.of(userRepository.save(user), null);
    }

    public UserResponseDto login(LoginRequestDto memberRequestDto) {
        // 1. Login ID/PW 를 기반으로 AuthenticationToken 생성
        UsernamePasswordAuthenticationToken authenticationToken = memberRequestDto.toAuthentication();
        // 2. 실제로 검증 (사용자 비밀번호 체크) 이 이루어지는 부분
        //    authenticate 메서드가 실행이 될 때 CustomUserDetailsService 에서 만들었던 loadUserByUsername 메서드가 실행됨
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);

        // 4. RefreshToken 저장
        RefreshToken refreshToken = RefreshToken.builder()
                .key(authentication.getName())
                .value(tokenDto.getRefreshToken())
                .build();

        refreshTokenRepository.save(refreshToken);

        User findUser = userRepository.findByEmail(memberRequestDto.getEmail())
                .orElseThrow(() -> new UserNotFoundException(memberRequestDto.getEmail()));

        // 5. 토큰 발급
        return UserResponseDto.of(findUser, tokenDto.getAccessToken());
    }

    public UserResponseDto updateUser(Principal principal, UpdateUserRequest dto, String token) {
        User findUser = userRepository.findById(Long.valueOf(principal.getName()))
                .orElseThrow(() -> new UserNotFoundException(Long.valueOf(principal.getName())));

        findUser.updateUser(dto.getEmail(), dto.getUserName(), dto.getImage(), dto.getBio());

        if (dto.getPassword() != null) {
            findUser.changePassword(passwordEncoder.encode(dto.getPassword()));
        }

        return UserResponseDto.of(findUser, resolveToken(token));
    }

    public TokenDto reissue(TokenRequestDto tokenRequestDto) {
        // 1. Refresh Token 검증
        if (!tokenProvider.validateToken(tokenRequestDto.getRefreshToken())) {
            throw new RuntimeException("Refresh Token 이 유효하지 않습니다.");
        }

        // 2. Access Token 에서 Member ID 가져오기
        Authentication authentication = tokenProvider.getAuthentication(tokenRequestDto.getAccessToken());

        // 3. 저장소에서 Member ID 를 기반으로 Refresh Token 값 가져옴
        RefreshToken refreshToken = refreshTokenRepository.findByUserKey(authentication.getName())
                .orElseThrow(() -> new RuntimeException("로그아웃 된 사용자입니다."));

        // 4. Refresh Token 일치하는지 검사
        if (!refreshToken.getValue().equals(tokenRequestDto.getRefreshToken())) {
            throw new RuntimeException("토큰의 유저 정보가 일치하지 않습니다.");
        }

        // 5. 새로운 토큰 생성
        TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);

        // 6. 저장소 정보 업데이트
        RefreshToken newRefreshToken = refreshToken.updateValue(tokenDto.getRefreshToken());
        refreshTokenRepository.save(newRefreshToken);

        // 토큰 발급
        return tokenDto;
    }
}