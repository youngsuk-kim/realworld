package com.example.realworld.controller;

import com.example.realworld.controller.dto.ProfileResponseDto;
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
    public ProfileResponseDto getProfile(@PathVariable String userName, Principal principal) {
        return profileService.getProfile(userName, principal);
    }

    @PostMapping("{userName}/follow")
    public ProfileResponseDto follow(@PathVariable String userName, Principal principal) {
        return profileService.follow(userName, principal);
    }

    @DeleteMapping("{userName}/follow")
    public ProfileResponseDto unfollow(@PathVariable String userName, Principal principal) {
        return profileService.unfollow(userName, principal);
    }
}
