package com.example.realworld.controller.dto;

import com.example.realworld.entity.Member;
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

    public static ProfileResponseDto of(Member member, Boolean isFollowing) {
        return ProfileResponseDto.builder()
                .bio(member.getBio())
                .image(member.getImage())
                .userName(member.getUserName())
                .following(isFollowing)
                .build();
    }
}
