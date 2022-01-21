package com.example.realworld.service;

import com.example.realworld.controller.dto.ProfileResponseDto;
import com.example.realworld.entity.Profile;
import com.example.realworld.entity.User;

import java.security.Principal;

public interface ProfileService {
    Profile getUserProfile(String followeeName, User follower);
    Profile follow(String followeeName, User follower);
    Profile unfollow(String followeeName, User follower);
}
