package com.example.activity.dto.comment;

import com.example.activity.model.Comment;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentResponseDTO {

    private Long id;
    private String content;
    private String userId;
    private List<CommentResponseDTO> children = new ArrayList<>();

    public CommentResponseDTO(Long id, String content, String userId) {
        this.id = id;
        this.content = content;
        this.userId = userId;
    }

    public static CommentResponseDTO convertCommentToDto(Comment comment) {
        return comment.getIsDeleted() ?
                new CommentResponseDTO(comment.getCommentId(), "삭제된 댓글입니다.", null) :
                new CommentResponseDTO(comment.getCommentId(), comment.getContent(), comment.getUserId());
    }
}