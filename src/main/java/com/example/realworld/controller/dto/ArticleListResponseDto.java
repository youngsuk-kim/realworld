package com.example.realworld.controller.dto;

import lombok.*;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@AllArgsConstructor
public class ArticleListResponseDto {
    private List<ArticleResponseDto> articles;
    private Integer articlesCount;
}
