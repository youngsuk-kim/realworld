package com.example.realworld;

import com.example.realworld.constant.Authority;
import com.example.realworld.entity.Profile;
import com.example.realworld.entity.User;

public class TestUtils {

    public static final String EMAIL = "thayer@naver.com";
    public static final String PASSWORD = "rladudtjr1!";
    public static final String ENCODE_PASSWORD = "encodePassword";
    public static final String USERNAME = "youngsuk";

    private TestUtils() {
    }

    public static User.UserBuilder aUser() {
        return User.builder()
                .profile(new Profile(USERNAME, EMAIL))
                .password(PASSWORD)
                .authority(Authority.ROLE_USER);
    }

}
