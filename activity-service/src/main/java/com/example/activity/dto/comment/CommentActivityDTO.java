package com.example.activity.dto.comment;

import com.example.activity.model.Category;
import com.example.activity.model.Comment;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CommentActivityDTO {
    private String userId;
    private Long commentId;
    private String content;
    private Comment comment;
    private Category category;

    public CommentActivityDTO(Comment comment) {
        this.comment = comment;
    }
}
