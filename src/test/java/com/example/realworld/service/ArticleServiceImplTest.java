package com.example.realworld.service;

import com.example.realworld.controller.dto.ArticleListResponseDto;
import com.example.realworld.controller.dto.ArticleResponseDto;
import com.example.realworld.repository.ArticleQueryRepository;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ArticleServiceImplTest {

    @Mock
    private ArticleQueryRepository articleQueryRepository;
    private ArticleService articleService;

    @BeforeEach
    void initializeUserService() {
        this.articleService = new ArticleServiceImpl(articleQueryRepository);
    }

    @Test
    void getArticles() {
        //given
        ArticleResponseDto articleResponseDto = new ArticleResponseDto(1L, "slug", "title",
                "description", "body", Lists.newArrayList("tag1", "tag2"),
                LocalDateTime.now(), LocalDateTime.now(), 1L);
        Integer articleCount = 1;
        String tag = "java";

        //when
        when(articleQueryRepository.getArticles(tag, null, null, null))
                .thenReturn(new ArticleListResponseDto(Lists.newArrayList(articleResponseDto), articleCount));

        ArticleListResponseDto response = articleService.getArticles(tag, null, null, null);

        //then
        assertEquals("slug", response.getArticles().get(0).getSlug());
    }

}