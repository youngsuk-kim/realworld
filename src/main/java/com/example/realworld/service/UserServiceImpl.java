package com.example.realworld.service;

import com.example.realworld.constant.Authority;
import com.example.realworld.controller.dto.*;
import com.example.realworld.entity.Profile;
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
        if (userRepository.existsByProfileEmail(dto.getEmail())) {
            throw new UserAlreadySignException(dto.getEmail());
        }

        User user = User.builder()
                .password(passwordEncoder.encode(dto.getPassword()))
                .profile(new Profile(dto.getUsername(), dto.getEmail()))
                .authority(Authority.ROLE_USER)
                .build();

        return userRepository.save(user);
    }

    public User findUserByEmail(String email) {
        return userRepository.findFirstByProfileEmail(email)
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