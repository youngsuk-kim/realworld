package com.example.realworld.controller.dto;

import com.example.realworld.constant.Authority;
import com.example.realworld.entity.User;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import static com.example.realworld.constant.Constants.JSON_TYPE_USER;

@JsonRootName(JSON_TYPE_USER)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SignUpRequestDto {

    private String email;
    private String password;
    private String username;
}
