package com.example.activity.controller;

import com.example.activity.service.LikeService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/activity/like")
public class LikeController {
    @Autowired
    LikeService likeService;

    @PostMapping("/post/{postId}")
    public ResponseEntity<?> likePost(HttpServletRequest request, @PathVariable Long postId) {
        likeService.likePost(request, postId);
        return ResponseEntity.ok().body("likes on post " + postId);
    }

    @DeleteMapping("/post/{postId}")
    public ResponseEntity<?> unlikePost(HttpServletRequest request, @PathVariable Long postId) {
        likeService.unlikePost(request, postId);
        return ResponseEntity.ok().body("unlikes on post " + postId);
    }

    @PostMapping("/comment/{commentId}")
    public ResponseEntity<?> likeComment(HttpServletRequest request, @PathVariable Long commentId) {
        likeService.likeComment(request, commentId);
        return ResponseEntity.ok().body("likes on comment " + commentId);
    }

    @DeleteMapping("/comment/{commentId}")
    public ResponseEntity<?> unlikeComment(HttpServletRequest request, @PathVariable Long commentId) {
        likeService.unlikeComment(request, commentId);
        return ResponseEntity.ok().body("unlikes on comment " + commentId);
    }
}
