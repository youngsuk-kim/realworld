package com.example.realworld.service;

import com.example.realworld.controller.dto.ProfileResponseDto;
import com.example.realworld.entity.Follow;
import com.example.realworld.entity.User;
import com.example.realworld.repository.FollowRepository;
import com.example.realworld.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final UserRepository userRepository;
    private final FollowRepository followRepository;

    @Override
    public ProfileResponseDto getProfile(String userName, Principal principal) {
        User findUser = userRepository.findByUsername(userName)
                .orElseThrow(() -> new RuntimeException("user not found"));

        User loginUser = userRepository.findById(Long.valueOf(principal.getName()))
                .orElseThrow(() -> new RuntimeException("login user not found"));

        boolean isFollowing = loginUser.getFollow().stream().anyMatch(f -> f.isFollowing(findUser));

        return ProfileResponseDto.of(findUser, isFollowing);
    }

    @Override
    public ProfileResponseDto follow(String userName, Principal principal) {
        User loginUser = userRepository.findById(Long.valueOf(principal.getName()))
                .orElseThrow(() -> new RuntimeException("login user not found"));

        User findUser = userRepository.findByUsername(userName)
                .orElseThrow(() -> new RuntimeException("user not found"));

        Follow saveFollow = followRepository.save(new Follow(findUser, loginUser));

        return ProfileResponseDto.of(loginUser, saveFollow.isFollowing(findUser));
    }

    @Override
    public ProfileResponseDto unfollow(String userName, Principal principal) {
        User loginUser = userRepository.findById(Long.valueOf(principal.getName()))
                .orElseThrow(() -> new RuntimeException("login user not found"));

        User findUser = userRepository.findByUsername(userName)
                .orElseThrow(() -> new RuntimeException("user not found"));

        Follow findFollow = followRepository.findByUserAndFollower(findUser, loginUser)
                        .orElseThrow(() -> new RuntimeException("follow delete row not found"));

        followRepository.delete(findFollow);

        return ProfileResponseDto.of(loginUser, findFollow.isFollowing(findUser));
    }
}
