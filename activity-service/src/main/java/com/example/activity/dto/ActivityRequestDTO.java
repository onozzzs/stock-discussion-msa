package com.example.activity.dto;

import com.example.activity.model.Activity;
import com.example.activity.model.Category;
import com.example.activity.model.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ActivityRequestDTO {
    private String userId;
    private String username;
    private Long postId;
    private Long commentId;
    private String postContent;
    private String commentContent;
    private Category category;


    public ActivityRequestDTO(Post post) {
        this.userId = post.getUserId();
        this.username = post.getUsername();
        this.postId = post.getId();
        this.postContent = post.getTitle();
    }
}
