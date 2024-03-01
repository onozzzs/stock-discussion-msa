package com.example.activity.dto;

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
    private Category category;
}
