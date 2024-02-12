package com.example.activity.controller;

import com.example.activity.dto.CommentRequestDTO;
import com.example.activity.model.Comment;
import com.example.activity.service.CommentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/activity/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @PostMapping("/post/{postId}")
    public ResponseEntity<?> commentOnPost(HttpServletRequest request, @PathVariable Long postId, @RequestBody CommentRequestDTO requestDTO) {
        Comment comment = Comment.builder()
                .content(requestDTO.getContent())
                .build();
        commentService.addComment(request, postId, comment);
        return ResponseEntity.ok().body("comment is saved");
    }
}
