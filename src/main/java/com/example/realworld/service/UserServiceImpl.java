package com.example.realworld.service;

import com.example.realworld.controller.dto.*;
import com.example.realworld.entity.User;
import com.example.realworld.exception.UserAlreadySignException;
import com.example.realworld.exception.UserNotFoundException;
import com.example.realworld.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User signup(SignUpRequestDto dto) {
        if (userRepository.existsByEmail(dto.getEmail())) {
            // TODO. 커스텀 익셉션 만들기
            throw new UserAlreadySignException(dto.getEmail());
        }

        return userRepository.save(dto.toUser(passwordEncoder));
    }

    public User login(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email));
    }

    public User updateUser(User user, UpdateUserRequest dto) {
        user.updateUser(dto.getEmail(), dto.getUserName(), dto.getImage(), dto.getBio());

        if (dto.getPassword() != null) {
            user.changePassword(passwordEncoder.encode(dto.getPassword()));
        }

        return user;
    }
}