package com.example.realworld.security;

import com.example.realworld.entity.User;
import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

@Getter
public class UserAdapter extends org.springframework.security.core.userdetails.User {

    private final User user;

    public UserAdapter(User user) {
        super(user.getEmail(),
                user.getPassword(),
                List.of(new SimpleGrantedAuthority("ROLE_USER")));
        this.user = user;
    }


}
