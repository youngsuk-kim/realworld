package com.example.realworld.service;

import com.example.realworld.controller.dto.ArticleListResponseDto;
import com.example.realworld.repository.ArticleQueryRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService{

    private final ArticleQueryRepo articleQueryRepo;

    @Override
    public ArticleListResponseDto getArticles(String tag, String author, String favorited, Long userId) {
        return articleQueryRepo.getArticles(tag, author, favorited, userId);
    }
}
