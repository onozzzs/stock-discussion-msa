package com.example.activity.dto;

import com.example.activity.model.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostRequestDTO {
    private String title;
    private String content;

    public static Post toEntity(PostRequestDTO postRequestDTO) {
        return Post.builder()
                .title(postRequestDTO.getTitle())
                .content(postRequestDTO.getContent())
                .build();
    }
}
