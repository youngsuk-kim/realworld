package com.example.realworld.controller.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthorResponseDto {
    @JsonIgnore
    private Long memberId;
    private String userName;
    private String bio;
    private String image;
    private Boolean following;
}
