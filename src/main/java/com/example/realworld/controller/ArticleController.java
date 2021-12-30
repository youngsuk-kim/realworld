package com.example.realworld.controller;

import com.example.realworld.controller.dto.ArticleListResponseDto;
import com.example.realworld.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/articles")
public class ArticleController {

    private final ArticleService articleService;

    @GetMapping
    public ArticleListResponseDto getArticles(@RequestParam(required = false) String tag,
                                              @RequestParam(required = false) String author,
                                              @RequestParam(required = false) String favorited,
                                              Principal principal) {
        return articleService.getArticles(tag, author, favorited, Long.valueOf(principal.getName()));
    }
}
