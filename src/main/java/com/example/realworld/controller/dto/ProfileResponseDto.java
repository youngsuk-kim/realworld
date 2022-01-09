package com.example.realworld.controller.dto;

import com.example.realworld.entity.User;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.*;

import static com.example.realworld.constant.Constants.JSON_TYPE_PROFILE;

@JsonRootName(JSON_TYPE_PROFILE)
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
public class ProfileResponseDto {
    private String userName;
    private String bio;
    private String image;
    private Boolean following;

    public static ProfileResponseDto of(User user, Boolean isFollowing) {
        return ProfileResponseDto.builder()
                .bio(user.getBio())
                .image(user.getImage())
                .userName(user.getUsername())
                .following(isFollowing)
                .build();
    }
}
