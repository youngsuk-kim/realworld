package com.example.realworld.service;

import com.example.realworld.controller.dto.ProfileResponseDto;
import com.example.realworld.entity.Follow;
import com.example.realworld.entity.User;
import com.example.realworld.repository.FollowRepo;
import com.example.realworld.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final UserRepo userRepo;
    private final FollowRepo followRepo;

    @Override
    public ProfileResponseDto getProfile(String userName, Principal principal) {
        User findUser = userRepo.findByUserName(userName)
                .orElseThrow(() -> new RuntimeException("user not found"));

        User loginUser = userRepo.findById(Long.valueOf(principal.getName()))
                .orElseThrow(() -> new RuntimeException("login user not found"));

        boolean isFollowing = loginUser.getFollow().stream().anyMatch(f -> f.isFollowing(findUser));

        return ProfileResponseDto.of(findUser, isFollowing);
    }

    @Override
    public ProfileResponseDto follow(String userName, Principal principal) {
        User loginUser = userRepo.findById(Long.valueOf(principal.getName()))
                .orElseThrow(() -> new RuntimeException("login user not found"));

        User findUser = userRepo.findByUserName(userName)
                .orElseThrow(() -> new RuntimeException("user not found"));

        Follow saveFollow = followRepo.save(new Follow(loginUser, findUser));

        return ProfileResponseDto.of(loginUser, saveFollow.isFollowing(findUser));
    }

    @Override
    public ProfileResponseDto unfollow(String userName, Principal principal) {
        User loginUser = userRepo.findById(Long.valueOf(principal.getName()))
                .orElseThrow(() -> new RuntimeException("login user not found"));

        User findUser = userRepo.findByUserName(userName)
                .orElseThrow(() -> new RuntimeException("user not found"));

        Follow findFollow = followRepo.findByUserAndFollower(findUser, loginUser)
                        .orElseThrow(() -> new RuntimeException("follow delete row not found"));

        followRepo.delete(findFollow);

        return ProfileResponseDto.of(loginUser, findFollow.isFollowing(findUser));
    }
}
