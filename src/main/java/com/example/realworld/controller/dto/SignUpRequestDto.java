package com.example.realworld.controller.dto;

import com.example.realworld.constant.Authority;
import com.example.realworld.entity.User;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import static com.example.realworld.constant.Constants.JSON_TYPE_USER;

@JsonRootName(JSON_TYPE_USER)
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class SignUpRequestDto {

    private String email;
    private String password;
    private String userName;

    public User toUser(PasswordEncoder passwordEncoder) {
        return User.builder()
                .userName(userName)
                .email(email)
                .password(passwordEncoder.encode(password))
                .authority(Authority.ROLE_USER)
                .build();
    }
}
