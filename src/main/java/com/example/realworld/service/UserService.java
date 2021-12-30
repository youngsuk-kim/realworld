package com.example.realworld.service;

import com.example.realworld.controller.dto.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

public interface UserService {
    MemberResponseDto getCurrentUser(Principal principal, HttpServletRequest request);
    MemberResponseDto signup(SignUpRequestDto dto);
    MemberResponseDto login(LoginRequestDto dto);
    MemberResponseDto updateUser(Principal principal, UpdateUserRequest dto, HttpServletRequest request);
    TokenDto reissue(TokenRequestDto tokenRequestDto);
}
