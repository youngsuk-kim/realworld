package com.example.realworld.controller;

import com.example.realworld.controller.dto.*;
import com.example.realworld.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/user")
    public ResponseEntity<UserResponseDto> currentUser(Principal principal, @RequestHeader(name = "Authorization") String token) {
        return ResponseEntity.ok(userService.getCurrentUser(principal, token));
    }

    @PostMapping("/users")
    public ResponseEntity<UserResponseDto> signup(@RequestBody SignUpRequestDto dto) {
        return ResponseEntity.ok(userService.signup(dto));
    }

    @PostMapping("/users/login")
    public ResponseEntity<UserResponseDto> login(@RequestBody LoginRequestDto dto) {
        return ResponseEntity.ok(userService.login(dto));
    }

    @PutMapping("/user")
    public ResponseEntity<UserResponseDto> updateUser(Principal principal, @RequestBody UpdateUserRequest dto, @RequestHeader(name = "Authorization") String token) {
        return ResponseEntity.ok(userService.updateUser(principal, dto, token));
    }

}