package com.example.realworld.service;

import com.example.realworld.controller.dto.ProfileResponseDto;

import java.security.Principal;

public interface ProfileService {
    ProfileResponseDto getProfile(String userName, Principal principal);
    ProfileResponseDto follow(String userName, Principal principal);
    ProfileResponseDto unfollow(String userName, Principal principal);
}
