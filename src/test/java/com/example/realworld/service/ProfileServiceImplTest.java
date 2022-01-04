package com.example.realworld.service;

import com.example.realworld.constant.Authority;
import com.example.realworld.controller.dto.ProfileResponseDto;
import com.example.realworld.entity.Follow;
import com.example.realworld.entity.User;
import com.example.realworld.repository.FollowRepository;
import com.example.realworld.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.AdditionalAnswers;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProfileServiceImplTest {

    private ProfileService profileService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private FollowRepository followRepository;

    @BeforeEach
    void initializeUserService() {
        this.profileService = new ProfileServiceImpl(userRepository, followRepository);
    }

    @Test
    void getProfile(@Mock Principal principal) {
        //given
        String userName = "youngsuk1";

        User user1 = new User(1L,userName,
                "thayer2@gmail.com", "rladudtjr1!",
                "bio", "imageurl", Authority.ROLE_USER);

        User user2 = new User(2L,"youngsuk2",
                "thayer2@gmail.com", "rladudtjr1!",
                "bio", "imageurl", Authority.ROLE_USER, new ArrayList<>());

        User user3 = new User(3L,"youngsuk3",
                "thayer3@gmail.com", "rladudtjr1!",
                "bio", "imageurl", Authority.ROLE_USER);

        //when
        when(principal.getName()).thenReturn("1");
        when(userRepository.findByUserName(userName)).thenReturn(Optional.of(user1));
        when(userRepository.findById(Long.valueOf(principal.getName()))).thenReturn(Optional.of(user2));

        ProfileResponseDto response = profileService.getProfile(userName, principal);

        //then
        assertEquals(user1.getUserName(), response.getUserName());
    }

    @Test
    void follow(@Mock Principal principal) {
        //given
        String userName = "youngsuk1";

        User user1 = new User(1L,userName,
                "thayer2@gmail.com", "rladudtjr1!",
                "bio", "imageurl", Authority.ROLE_USER);

        User user2 = new User(2L,"youngsuk2",
                "thayer2@gmail.com", "rladudtjr1!",
                "bio", "imageurl", Authority.ROLE_USER);

        Follow follow = new Follow(1L, user1, user2);

        //when
        when(principal.getName()).thenReturn("1");
        when(userRepository.findByUserName(userName)).thenReturn(Optional.of(user1));
        when(userRepository.findById(Long.valueOf(principal.getName()))).thenReturn(Optional.of(user2));
        when(followRepository.save(any(Follow.class))).then(AdditionalAnswers.returnsFirstArg());
        ProfileResponseDto response = profileService.follow(userName, principal);

        //then
        assertEquals(follow.getFollower().getUserName(), response.getUserName());
    }

    @Test
    void unfollow(@Mock Principal principal) {
        //given
        String userName = "youngsuk1";

        User user1 = new User(1L,userName,
                "thayer2@gmail.com", "rladudtjr1!",
                "bio", "imageurl", Authority.ROLE_USER);

        User user2 = new User(2L,"youngsuk2",
                "thayer2@gmail.com", "rladudtjr1!",
                "bio", "imageurl", Authority.ROLE_USER);

        Follow follow = new Follow(1L, user1, user2);

        //when
        when(principal.getName()).thenReturn("1");
        when(userRepository.findByUserName(userName)).thenReturn(Optional.of(user1));
        when(userRepository.findById(Long.valueOf(principal.getName()))).thenReturn(Optional.of(user2));
        when(followRepository.findByUserAndFollower(user1, user2)).thenReturn(Optional.of(follow));
        profileService.unfollow(userName, principal);

        //then
        verify(followRepository, times(1)).delete(follow);

    }

}