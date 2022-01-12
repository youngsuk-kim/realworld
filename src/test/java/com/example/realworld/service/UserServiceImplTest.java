package com.example.realworld.service;

import com.example.realworld.controller.dto.SignUpRequestDto;
import com.example.realworld.controller.dto.UpdateUserRequest;
import com.example.realworld.entity.User;
import com.example.realworld.exception.UserAlreadySignException;
import com.example.realworld.exception.UserNotFoundException;
import com.example.realworld.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static com.example.realworld.TestUtils.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void when_signup_email_duplicate_create_exception() {
        //given
        SignUpRequestDto dto = new SignUpRequestDto(EMAIL, PASSWORD, USERNAME);

        //when
        when(userRepository.existsByEmail(EMAIL)).thenReturn(true);

        //then
        assertThrows(UserAlreadySignException.class, () -> userService.signup(dto));
    }

    @Test
    void when_signup_encode_password_create() {
        //given
        when(passwordEncoder.encode(PASSWORD)).thenReturn(ENCODE_PASSWORD);

        //then
        assertThat(ENCODE_PASSWORD).isEqualTo(passwordEncoder.encode(PASSWORD));
    }

    @Test
    void when_findUserByEmail_user_not_found_create_exception() {
        //when
        when(userRepository.findByEmail(EMAIL)).thenThrow(new UserNotFoundException(EMAIL));

        //then
        assertThrows(UserNotFoundException.class, () -> userService.findUserByEmail(EMAIL));
    }

    @Test
    void when_updateUser_email_updated() {
        //given
        String changeEmail = "thayer2@naver.com";
        User user = aUser().build();
        UpdateUserRequest dto = UpdateUserRequest.builder()
                .email(changeEmail)
                .build();

        //when
        User changeUser = userService.updateUser(user, dto);

        //then
        assertThat(changeEmail).isEqualTo(changeUser.getEmail());
    }

    @Test
    void when_updateUser_userName_updated() {
        //given
        String changeUserName = "youngsuk2";
        User user = aUser().build();
        UpdateUserRequest dto = UpdateUserRequest.builder()
                .userName(changeUserName)
                .build();

        //when
        User changeUser = userService.updateUser(user, dto);

        //then
        assertThat(changeUserName).isEqualTo(changeUser.getUsername());
    }

    @Test
    void when_updateUser_password_updated() {
        //given
        String changePassword = "rladudtjr1@";
        String encodeChangePassword = "encodeChangePass";
        when(passwordEncoder.encode(changePassword)).thenReturn(encodeChangePassword);

        User user = aUser().password(passwordEncoder.encode(changePassword)).build();
        UpdateUserRequest dto = UpdateUserRequest.builder()
                .password(changePassword)
                .build();

        //when
        User changeUser = userService.updateUser(user, dto);

        //then
        assertThat(encodeChangePassword).isEqualTo(changeUser.getPassword());
    }


}