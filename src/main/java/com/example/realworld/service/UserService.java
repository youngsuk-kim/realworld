package com.example.realworld.service;

import com.example.realworld.controller.dto.*;
import com.example.realworld.entity.User;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

public interface UserService {
    User signup(SignUpRequestDto dto);
    User findUserByEmail(String email);
    User updateUser(User user, UpdateUserRequest dto);
}
