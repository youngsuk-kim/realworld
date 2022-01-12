package com.example.realworld.controller;

import com.example.realworld.annotation.AuthUser;
import com.example.realworld.controller.dto.*;
import com.example.realworld.entity.RefreshToken;
import com.example.realworld.entity.User;
import com.example.realworld.service.TokenService;
import com.example.realworld.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final TokenService tokenService;

    @GetMapping("/user")
    public ResponseEntity<UserResponseDto> currentUser(@AuthUser User user) {
        return ResponseEntity.ok(UserResponseDto.of(user, getCurrentCredential()));
    }

    @PostMapping("/users")
    public ResponseEntity<UserResponseDto> signup(@RequestBody SignUpRequestDto dto) {
        return ResponseEntity.ok(UserResponseDto.of(userService.signup(dto), null));
    }

    @PostMapping("/users/login")
    public ResponseEntity<UserResponseDto> login(@RequestBody LoginRequestDto dto) {
        String token = tokenService.createToken(dto.toAuthentication()).getValue();
        User user = userService.findUserByEmail(dto.getEmail());
        return ResponseEntity.ok(UserResponseDto.of(user, token));
    }

    @PutMapping("/user")
    public ResponseEntity<UserResponseDto> updateUser(@AuthUser User user, @RequestBody UpdateUserRequest dto) {
        return ResponseEntity.ok(UserResponseDto.of(userService.updateUser(user, dto), getCurrentCredential()));
    }

    private String getCurrentCredential() {
        return SecurityContextHolder.getContext()
                .getAuthentication()
                .getCredentials()
                .toString();
    }

}