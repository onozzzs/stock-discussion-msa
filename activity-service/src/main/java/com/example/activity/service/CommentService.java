package com.example.activity.service;

import com.example.activity.dto.ActivityRequestDTO;
import com.example.activity.model.Category;
import com.example.activity.model.Comment;
import com.example.activity.repository.CommentRepository;
import com.example.activity.model.Post;
import com.example.activity.repository.PostRepository;
import com.example.user.model.User;
import com.example.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostActivityServiceImpl activityService;

    @Autowired
    private TokenProvider tokenProvider;

    public void addComment(HttpServletRequest request, final Long postId, Comment comment) {
        String userId = tokenProvider.getUserId(request);

        User user = userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException("user not found"));
        Post post = postRepository.findById(postId).orElseThrow(() -> new NoSuchElementException("post not found"));

        comment.setUser(user);
        comment.setPost(post);

        commentRepository.save(comment);

        ActivityRequestDTO activityRequestDTO = ActivityRequestDTO.builder()
                .username(user.getUsername())
                .userId(user.getId())
                .category(Category.COMMENT)
                .targetName(String.valueOf(post.getId()))
                .build();

        activityService.makeAndSaveActivity(activityRequestDTO);
    }
}
