package com.example.activity.controller;

import com.example.activity.dto.PostRequestDTO;
import com.example.activity.dto.PostResponseDTO;
import com.example.activity.dto.SearchDTO;
import com.example.activity.model.Post;
import com.example.activity.service.PostService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

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

    @GetMapping("/{ticker}")
    public ResponseEntity<?> retrieveBoard(@PathVariable String ticker, Pageable pageable) {
        Page<Post> posts = postService.retrieveBoard(pageable, ticker);
        return ResponseEntity.ok().body(posts);
    }

    @GetMapping("/search")
    public ResponseEntity<?> search(@RequestBody SearchDTO searchDTO) {
        Page<Post> posts = postService.search(searchDTO);
        return ResponseEntity.ok().body(posts);
    }

//    @GetMapping
//    public ResponseEntity<?> getPost(HttpServletRequest request) {
//        List<Post> posts = postService.getPost(request);
//        List<PostResponseDTO> responseDTOs = posts.stream().map(PostResponseDTO::new).collect(Collectors.toList());
//        return ResponseEntity.ok().body(responseDTOs);
//    }
//
//    @GetMapping("/follower")
//    public ResponseEntity<?> getFollowerPost(HttpServletRequest request) {
//        List<Post> posts = postService.getFollowerPost(request);
//        List<PostResponseDTO> responseDTOs = posts.stream().map(PostResponseDTO::new).collect(Collectors.toList());
//        return ResponseEntity.ok().body(responseDTOs);
//    }
//
//    @PutMapping("/{postId}")
//    public ResponseEntity<?> updatePost(HttpServletRequest request, @PathVariable Long postId, @RequestBody PostRequestDTO postRequestDTO) {
//        Post post = PostRequestDTO.toEntity(postRequestDTO);
//        post.setId(postId);
//        postService.updatePost(postId, post);
//        return ResponseEntity.ok().body("update is complete");
//    }
//
//    @DeleteMapping("/{postId}")
//    public ResponseEntity<?> deletePost(HttpServletRequest request, @PathVariable Long postId) {
//        postService.deletePost(request, postId);
//        return ResponseEntity.ok().body(postId + " is deleted");
//    }
}
