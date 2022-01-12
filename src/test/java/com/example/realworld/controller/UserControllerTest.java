package com.example.realworld.controller;

import com.example.realworld.controller.dto.UserResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static com.example.realworld.TestUtils.*;
import static java.lang.String.format;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// TODO. 해당 어노테이션 기능 알아보기
@TestInstance(PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
// TODO. 해당 어노테이션 기능 알아보기
@AutoConfigureMockMvc
@SpringBootTest
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private String token;

    @Order(1)
    @Test
    void signup() throws Exception {
        mockMvc.perform(post("/api/users")
                .contentType(APPLICATION_JSON)
                .content(format("{\"user\": {\"username\": \"%s\",\"email\": \"%s\", \"password\": \"%s\"}}", USERNAME, EMAIL, PASSWORD)))
                .andExpect(status().isOk());
    }

    @Order(2)
    @Test
    void login_and_remember_token() throws Exception{
        final var contentAsString = mockMvc.perform(post("/api/users/login")
                        .contentType(APPLICATION_JSON)
                        .content(format("{\"user\":{\"email\":\"%s\", \"password\":\"%s\"}}", EMAIL, PASSWORD)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        UserResponseDto userResponseDto = objectMapper.readValue(contentAsString, UserResponseDto.class);
        token = userResponseDto.getToken();
    }

    @Order(3)
    @Test
    void current_user() throws Exception {
        mockMvc.perform(get("/api/user")
                        .header(AUTHORIZATION, "Token " + token))
                .andExpect(status().isOk());
    }
}