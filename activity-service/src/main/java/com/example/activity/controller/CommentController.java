package com.example.activity.controller;

import com.example.activity.dto.comment.CommentRequestDTO;
import com.example.activity.dto.post.PostResponseDTO;
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

    @GetMapping("/{postId}")
    public ResponseEntity<?> getBoard(HttpServletRequest request, @PathVariable(value = "postId") Long postId) {
        PostResponseDTO postResponseDTOs = commentService.getBoard(request, postId);
        return ResponseEntity.ok().body(postResponseDTOs);
    }

    @PostMapping("/{postId}")
    public ResponseEntity<?> comment(HttpServletRequest request, @PathVariable(value = "postId") Long postId, @RequestBody CommentRequestDTO requestDTO) {
        commentService.saveComment(request, postId, requestDTO);
        return ResponseEntity.ok().body("comment is saved");
    }
}
