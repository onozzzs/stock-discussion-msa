package com.example.activity.service;

import com.example.activity.model.Comment;
import com.example.activity.model.Like;
import com.example.activity.repository.CommentRepository;
import com.example.activity.repository.LikeRepository;
import com.example.activity.model.Post;
import com.example.user.model.User;
import com.example.activity.repository.PostRepository;
import com.example.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.NoSuchElementException;

@Service
public class LikeService {
    @Autowired
    private LikeRepository likeRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private TokenProvider tokenProvider;

    @Transactional
    public void likePost(HttpServletRequest request, Long postId) {
        String userId = tokenProvider.getUserId(request);
        User user = userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException("user not found"));
        Post post = postRepository.findById(postId).orElseThrow(() -> new NoSuchElementException("post not found"));

        if (likeRepository.findByUserIdAndPostId(userId, postId).isPresent()) {
            throw new RuntimeException("like already exists");
        }

        Like like = Like.builder()
                .user(user)
                .post(post)
                .build();

        post.updateLikeCount(post.getLikeCount() + 1);

        likeRepository.save(like);
    }

    @Transactional
    public void unlikePost(HttpServletRequest request, Long postId) {
        String userId = tokenProvider.getUserId(request);
        Like like = likeRepository.findByUserIdAndPostId(userId, postId).orElseThrow(() -> new NoSuchElementException("like not found"));
        Post post = postRepository.findById(postId).orElseThrow(() -> new NoSuchElementException("post not found"));
        post.updateLikeCount(post.getLikeCount() - 1);
        likeRepository.delete(like);
    }

    @Transactional
    public void likeComment(HttpServletRequest request, Long commentId) {
        String userId = tokenProvider.getUserId(request);
        User user = userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException("user not found"));
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new NoSuchElementException("comment not found"));

        if (likeRepository.findByUserIdAndCommentId(userId, commentId).isPresent()) {
            throw new RuntimeException("like already exists");
        }

        Like like = Like.builder()
                .user(user)
                .comment(comment)
                .build();

        comment.updateLikeCount(comment.getLikeCount() + 1);

        likeRepository.save(like);
    }

    @Transactional
    public void unlikeComment(HttpServletRequest request, Long commentId) {
        String userId = tokenProvider.getUserId(request);
        Like like = likeRepository.findByUserIdAndCommentId(userId, commentId).orElseThrow(() -> new NoSuchElementException("like not found"));
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new NoSuchElementException("comment not found"));
        comment.updateLikeCount(comment.getLikeCount() - 1);
        likeRepository.delete(like);
    }
}