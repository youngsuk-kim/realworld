package com.example.realworld.exception;

public class UserAlreadySignException extends RuntimeException{

    public UserAlreadySignException(String email) {
        super("이미 가입되어있는 이메일 입니다 = " + email);
    }
}
