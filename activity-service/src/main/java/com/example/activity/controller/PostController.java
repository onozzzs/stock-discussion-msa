package com.example.activity.controller;

import com.example.activity.dto.post.PostRequestDTO;
import com.example.activity.dto.SearchDTO;
import com.example.activity.dto.post.PostResponseDTO;
import com.example.activity.dto.post.PostUpdateRequestDTO;
import com.example.activity.service.PostService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/post")
public class PostController {
    @Autowired
    private PostService postService;

    @PostMapping
    public ResponseEntity<?> savePost(@RequestBody PostRequestDTO requestDTO, HttpServletRequest request) {
        postService.savePost(request, requestDTO);
        return ResponseEntity.ok().body("Post is saved");
    }

    @GetMapping("/board")
    public ResponseEntity<?> retrieveBoard(@RequestParam(value = "ticker") String ticker, Pageable pageable) {
        Page<PostResponseDTO> posts = postService.retrieveBoard(pageable, ticker);
        return ResponseEntity.ok().body(posts);
    }

    @GetMapping("/my")
    public ResponseEntity<?> getMyPost(HttpServletRequest request, Pageable pageable) {
        Page<PostResponseDTO> posts = postService.getMyPost(request, pageable);
        return ResponseEntity.ok().body(posts);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllPost(HttpServletRequest request) {
        Page<PostResponseDTO> posts = postService.getAllPost(request);
        return ResponseEntity.ok().body(posts);
    }

    @PutMapping("/{postId}")
    public ResponseEntity<?> updatePost(HttpServletRequest request, @PathVariable(value = "postId") Long postId, @RequestBody PostUpdateRequestDTO requestDTO) {
        postService.updatePost(postId, requestDTO);
        return ResponseEntity.ok().body("update is complete");
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<?> deletePost(HttpServletRequest request, @PathVariable(value = "postId") Long postId) {
        postService.deletePost(postId);
        return ResponseEntity.ok().body(postId + " is deleted");
    }

    @GetMapping("/search")
    public ResponseEntity<?> search(@RequestBody SearchDTO searchDTO) {
        Page<PostResponseDTO> posts = postService.search(searchDTO);
        return ResponseEntity.ok().body(posts);
    }
}
