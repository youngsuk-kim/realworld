package com.example.realworld.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = (String)authentication.getCredentials();

        UserAdapter userAdapter = (UserAdapter) userDetailsService.loadUserByUsername(username);

        if(!passwordEncoder.matches(password, userAdapter.getUser().getPassword())){
            throw new BadCredentialsException("패스워드가 일치하지 않습니다.");
        }

        return new UsernamePasswordAuthenticationToken(
                userAdapter,
        null,
                userAdapter.getAuthorities());
    }

    // 토큰 타입과 일치할 때 인증
    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
