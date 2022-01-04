package com.example.realworld.service;

import com.example.realworld.constant.Authority;
import com.example.realworld.controller.dto.*;
import com.example.realworld.entity.User;
import com.example.realworld.exception.UserNotFoundException;
import com.example.realworld.repository.RefreshTokenRepository;
import com.example.realworld.repository.UserRepository;
import com.example.realworld.security.TokenProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class UserServiceImplTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManagerBuilder authenticationManagerBuilder;

    @Mock
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private TokenProvider tokenProvider;

    private UserService userService;

    @BeforeEach
    void initializeUserService() {
        this.userService = new UserServiceImpl(authenticationManagerBuilder, userRepository,
                passwordEncoder, tokenProvider, refreshTokenRepository);
    }

    @Test
    void updateUser(@Mock UpdateUserRequest updateUserRequest, @Mock Principal principal) {
        //given
        User user = new User("youngsuk",
                "thayer@gmail.com", passwordEncoder.encode("rladudtjr1!"),
                "bio", "imageurl", Authority.ROLE_USER);

        userRepository.saveAndFlush(user);

        TokenDto tokenDto = tokenProvider.generateTokenDto(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));

        //when
        when(updateUserRequest.getEmail()).thenReturn("youngsuk2");
        when(updateUserRequest.getPassword()).thenReturn(passwordEncoder.encode("rladudtjr1"));
        when(updateUserRequest.getBio()).thenReturn("bio2");
        when(updateUserRequest.getUserName()).thenReturn("youngsuk2");
        when(updateUserRequest.getImage()).thenReturn("imageurl2");
        when(principal.getName()).thenReturn("1");
        userService.updateUser(principal, updateUserRequest, tokenDto.getAccessToken());

        //then
    }

    @Test
    void getCurrentUser(@Mock Principal principal) {
        //given
        User user = new User("youngsuk",
                "thayer@gmail.com", passwordEncoder.encode("rladudtjr1!"),
                "bio", "imageurl", Authority.ROLE_USER);

        userRepository.saveAndFlush(user);

        TokenDto tokenDto = tokenProvider.generateTokenDto(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));

        //when
        when(principal.getName()).thenReturn("1");
        UserResponseDto userResponse = userService.getCurrentUser(principal, tokenDto.getAccessToken());

        //then
        assertEquals("thayer@gmail.com", userResponse.getEmail());
    }

    @Test
    void signUp(@Mock SignUpRequestDto signUpRequestDto) {
        //given
        User user = new User("youngsuk",
                "thayer@gmail.com", "rladudtjr1!",
                "bio", "imageurl", Authority.ROLE_USER);

        given(userRepository.findById(1L)).willReturn(Optional.of(user));

        //when
        when(signUpRequestDto.toUser(passwordEncoder)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(user);
        userService.signup(signUpRequestDto);

        //then
        User findUser = userRepository.findById(1L)
                .orElseThrow(() -> new UserNotFoundException(1L));

        assertEquals(user, findUser);
    }

    @Test
    @Transactional
    void login(@Mock LoginRequestDto loginRequestDto) {
        //given
        User user = new User("youngsuk",
                "thayer@gmail.com", passwordEncoder.encode("rladudtjr1!"),
                "bio", "imageurl", Authority.ROLE_USER);

        userRepository.saveAndFlush(user);

        //when
        when(loginRequestDto.getEmail()).thenReturn("thayer@gmail.com");
        when(loginRequestDto.toAuthentication()).thenReturn(new UsernamePasswordAuthenticationToken("thayer@gmail.com", "rladudtjr1!"));
        UserResponseDto loginResponse = userService.login(loginRequestDto);

        //then
        assertTrue(tokenProvider.validateToken(loginResponse.getToken()));
        assertEquals("1", tokenProvider.parseClaims(loginResponse.getToken()).getSubject());
    }

}