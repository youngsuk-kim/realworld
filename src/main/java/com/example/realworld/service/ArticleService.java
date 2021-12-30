package com.example.realworld.service;

import com.example.realworld.controller.dto.ArticleListResponseDto;

public interface ArticleService {
    ArticleListResponseDto getArticles(String tag, String author, String favorited, Long userId);
}
