package com.example.realworld.service;

import com.example.realworld.controller.dto.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

public interface UserService {
    UserResponseDto getCurrentUser(Principal principal, String token);
    UserResponseDto signup(SignUpRequestDto dto);
    UserResponseDto login(LoginRequestDto dto);
    UserResponseDto updateUser(Principal principal, UpdateUserRequest dto, String token);
}
