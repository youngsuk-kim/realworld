package com.example.realworld.controller;

import com.example.realworld.controller.dto.*;
import com.example.realworld.service.UserService;
import com.example.realworld.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import java.security.Principal;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/user")
    public ResponseEntity<MemberResponseDto> currentUser(Principal principal, HttpServletRequest httpRequest) {
        return ResponseEntity.ok(userService.getCurrentUser(principal, httpRequest));
    }

    @PostMapping("/users")
    public ResponseEntity<MemberResponseDto> signup(@RequestBody SignUpRequestDto dto) {
        return ResponseEntity.ok(userService.signup(dto));
    }

    @PostMapping("/users/login")
    public ResponseEntity<MemberResponseDto> login(@RequestBody LoginRequestDto dto) {
        return ResponseEntity.ok(userService.login(dto));
    }

    @PutMapping("/user")
    public ResponseEntity<MemberResponseDto> updateUser(Principal principal, @RequestBody UpdateUserRequest dto, HttpServletRequest request) {
        return ResponseEntity.ok(userService.updateUser(principal, dto, request));
    }

    @PostMapping("/reissue")
    public ResponseEntity<TokenDto> reissue(@RequestBody TokenRequestDto tokenRequestDto) {
        return ResponseEntity.ok(userService.reissue(tokenRequestDto));
    }
}