package com.example.realworld.controller.dto;

import com.example.realworld.entity.User;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;

import static com.example.realworld.constant.Constants.JSON_TYPE_USER;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.As.WRAPPER_OBJECT;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME;
import lombok.*;

@JsonTypeName(JSON_TYPE_USER)
@JsonTypeInfo(include = WRAPPER_OBJECT, use = NAME)
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserResponseDto {
    private String email;
    private String token;
    private String userName;
    private String bio;
    private String image;

    public static UserResponseDto of(User user, String accessToken) {
        return UserResponseDto.builder()
                .bio(user.getBio())
                .image(user.getImage())
                .userName(user.getUserName())
                .email(user.getEmail())
                .token(accessToken)
                .build();
    }
}
