package com.example.realworld.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.example.realworld.security.SecurityUtil.resolveToken;

@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final TokenProvider tokenProvider;
    private final UserDetailsService userDetailsService;

    // JWT 토큰의 인증 정보를 현재 쓰레드의 SecurityContext 에 저장하는 역할 수행
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {

        String jwt = resolveToken(request);

        if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
            User user = tokenProvider.getUser(jwt);
            Authentication authenticationToken = new UsernamePasswordAuthenticationToken(
                    userDetailsService.loadUserByUsername(user.getUsername()),
                    jwt, user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }

        filterChain.doFilter(request, response);
    }
}

