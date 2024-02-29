package com.example.activity.dto.post;

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
    private String ticker;
    private String content;

    public static Post toEntity(PostRequestDTO postRequestDTO) {
        return Post.builder()
                .title(postRequestDTO.getTitle())
                .ticker(postRequestDTO.getTicker())
                .content(postRequestDTO.getContent())
                .build();
    }
}
