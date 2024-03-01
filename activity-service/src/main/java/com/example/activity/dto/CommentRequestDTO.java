package com.example.activity.dto;

import com.example.activity.model.Comment;
import lombok.Data;

@Data
public class CommentRequestDTO {
    private Long parentId;
    private String content;

    public static Comment toEntity(CommentRequestDTO dto) {
        return Comment.builder()
                .content(dto.getContent())
                .build();
    }
}
