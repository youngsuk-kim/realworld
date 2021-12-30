package com.example.realworld.controller.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ArticleResponseDto {

    public ArticleResponseDto(Long articleId, String slug, String title, String description, String body, List<String> tagList, LocalDateTime createdAt, LocalDateTime updatedAt, Long likeCount) {
        this.articleId = articleId;
        this.slug = slug;
        this.title = title;
        this.description = description;
        this.body = body;
        this.tagList = tagList;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.likeCount = likeCount;
    }

    @JsonIgnore
    private Long articleId;
    private String slug;
    private String title;
    private String description;
    private String body;
    private List<String> tagList;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Boolean favorited;
    private Long favoritesCount;
    private AuthorResponseDto author;
    private Long likeCount;
}
