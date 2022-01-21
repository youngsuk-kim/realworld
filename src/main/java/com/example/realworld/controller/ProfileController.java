package com.example.realworld.controller;

import com.example.realworld.annotation.AuthUser;
import com.example.realworld.controller.dto.ProfileResponseDto;
import com.example.realworld.entity.User;
import com.example.realworld.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/profiles")
public class ProfileController {

    private final ProfileService profileService;

    @GetMapping("{userName}")
    public ProfileResponseDto getProfile(@PathVariable String userName, @AuthUser User user) {
        return ProfileResponseDto.of(profileService.getUserProfile(userName, user));
    }

    @PostMapping("{userName}/follow")
    public ProfileResponseDto follow(@PathVariable String userName, @AuthUser User user) {
        return ProfileResponseDto.of(profileService.follow(userName, user));
    }

    @DeleteMapping("{userName}/follow")
    public ProfileResponseDto unfollow(@PathVariable String userName, @AuthUser User user) {
        return ProfileResponseDto.of(profileService.unfollow(userName, user));
    }
}
