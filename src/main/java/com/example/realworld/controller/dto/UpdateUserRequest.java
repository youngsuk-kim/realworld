package com.example.realworld.controller.dto;

import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.example.realworld.constant.Constants.JSON_TYPE_USER;

@JsonRootName(JSON_TYPE_USER)
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UpdateUserRequest {
    private String email;
    private String userName;
    private String password;
    private String image;
    private String bio;
}
