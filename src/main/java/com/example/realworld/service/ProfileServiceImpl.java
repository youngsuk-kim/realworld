package com.example.realworld.service;

import com.example.realworld.entity.Profile;
import com.example.realworld.entity.User;
import com.example.realworld.exception.UserNotFoundException;
import com.example.realworld.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final UserRepository userRepository;

    @Override
    public Profile getUserProfile(String followeeName, User follower) {
        User followee = getUser(followeeName);

        return getProfile(follower, followee);
    }

    @Override
    public Profile follow(String followeeName, User follower) {
        User followee = getUser(followeeName);

        followee.addFollower(follower);

        return getProfile(follower, followee);
    }

    @Override
    public Profile unfollow(String followeeName, User follower) {
        User followee = getUser(followeeName);

        followee.unfollow(follower);

        return getProfile(follower, followee);
    }

    private Profile getProfile(User follower, User followee) {
        return new Profile(follower.getUsername(), follower.getEmail(), follower.getBio(), follower.getImage(), follower.isFollowing(followee));
    }

    private User getUser(String followeeName) {
        return userRepository.findFirstByProfileUsername(followeeName)
                .orElseThrow(() -> new UserNotFoundException(followeeName));
    }

}
