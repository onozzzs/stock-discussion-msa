package com.example.activity.dto.post;

import com.example.activity.dto.comment.CommentResponseDTO;
import com.example.activity.model.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostResponseDTO {
    private String title;
    private String content;
    private String username;
    private int likeCount;
    private List<CommentResponseDTO> commentResponseDTOList;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public PostResponseDTO(Post post) {
        this.title = post.getTitle();
        this.content = post.getContent();
        this.username = post.getUsername();
        this.likeCount = post.getLikeCount();
        this.createdAt = post.getCreatedAt();
        this.updatedAt = post.getUpdatedAt();
    }
}
