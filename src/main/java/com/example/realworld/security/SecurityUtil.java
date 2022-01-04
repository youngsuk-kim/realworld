package com.example.realworld.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

import static com.example.realworld.constant.Constants.AUTHORIZATION_HEADER;
import static com.example.realworld.constant.Constants.Token_PREFIX;

@Slf4j
public class SecurityUtil {


    private SecurityUtil() { }

    // SecurityContext 에 유저 정보가 저장되는 시점
    // Request 가 들어올 때 JwtFilter 의 doFilter 에서 저장
    public static Long getCurrentMemberId() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || authentication.getName() == null) {
            throw  new RuntimeException("Security Context 에 인증 정보가 없습니다.");
        }

        return Long.parseLong(authentication.getName());
    }

    // Request Header 에서 토큰 정보를 꺼내오기
    public static String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(Token_PREFIX)) {
            return bearerToken.substring(6);
        }
        return null;
    }

    public static String resolveToken(String token) {
        if (StringUtils.hasText(token) && token.startsWith(Token_PREFIX)) {
            return token.substring(6);
        }
        return null;
    }
}