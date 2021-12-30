package com.example.realworld.controller.dto;

import com.example.realworld.entity.Member;
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
public class MemberResponseDto {
    private String email;
    private String token;
    private String userName;
    private String bio;
    private String image;

    public static MemberResponseDto of(Member member, String accessToken) {
        return MemberResponseDto.builder()
                .bio(member.getBio())
                .image(member.getImage())
                .userName(member.getUserName())
                .email(member.getEmail())
                .token(accessToken)
                .build();
    }
}
