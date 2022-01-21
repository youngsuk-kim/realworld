package com.example.realworld.controller;

import com.example.realworld.TestUtils;
import com.example.realworld.entity.User;
import com.example.realworld.repository.UserRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

@TestInstance(PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
// TODO. 해당 어노테이션 기능 알아보기
@AutoConfigureMockMvc
@SpringBootTest
public class ProfileControllerTest {

    @Autowired
    UserRepository userRepository;

    @BeforeAll
    void setup() {
        User user1 = TestUtils.aUser().build();
    }
}
