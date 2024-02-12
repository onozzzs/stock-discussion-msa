package com.example.newsfeed.dto;

import com.example.newsfeed.model.Newsfeed;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class NewsfeedResponseDTO {
    String userId;
    Long postId;
    String content;

    public NewsfeedResponseDTO(Newsfeed newsfeed) {
        this.userId = newsfeed.getUserId();
        this.postId = newsfeed.getPostId();
        this.content = newsfeed.getContent();
    }
}
