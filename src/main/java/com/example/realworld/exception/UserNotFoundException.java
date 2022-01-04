package com.example.realworld.exception;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(Long id) {
        super("유저를 찾을 수 없습니다. id = " + id);
    }

    public UserNotFoundException(String email) {
        super("유저를 찾을 수 없습니다. email = " + email);
    }
}
