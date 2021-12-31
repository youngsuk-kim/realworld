package com.example.realworld.service;

import com.example.realworld.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class UserServiceImplTest {

    @Autowired
    private UserRepo userRepo;

    void 회원가입_유저가_디비에_잘_들어가는지() {
    }

}