package com.example.realworld.service;

import com.example.realworld.controller.dto.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

public interface UserService {
    UserResponseDto getCurrentUser(Principal principal, HttpServletRequest request);
    UserResponseDto signup(SignUpRequestDto dto);
    UserResponseDto login(LoginRequestDto dto);
    UserResponseDto updateUser(Principal principal, UpdateUserRequest dto, HttpServletRequest request);
    TokenDto reissue(TokenRequestDto tokenRequestDto);
}
