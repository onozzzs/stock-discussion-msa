package com.example.activity.repository;

import com.example.activity.dto.comment.CommentResponseDTO;
import com.example.activity.model.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentCustomRepository {

    List<CommentResponseDTO> findByPostId(Long id);

    Optional<Comment> findCommentByIdWithParent(Long id);
}
