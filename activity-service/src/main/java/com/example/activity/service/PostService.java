package com.example.activity.service;

import com.example.activity.api.FollowAPI;
import com.example.activity.dto.ActivityRequestDTO;
import com.example.activity.dto.FollowDTO;
import com.example.activity.dto.PostRequestDTO;
import com.example.activity.model.Category;
import com.example.user.model.Follow;
import com.example.activity.model.Post;
import com.example.activity.repository.PostRepository;
import com.example.user.model.User;
import com.example.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostActivityServiceImpl activityService;

    @Autowired
    private TokenProvider tokenProvider;

    @Autowired
    private FollowAPI followAPI;

    public void savePost(HttpServletRequest request, PostRequestDTO requestDTO) {
        String userId = tokenProvider.getUserId(request);
        User writer = userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException("user not found"));

        Post post = Post.builder()
                .title(requestDTO.getTitle())
                .content(requestDTO.getContent())
                .build();

        post.setWriter(writer);
        postRepository.save(post);

        ActivityRequestDTO activityRequestDTO = ActivityRequestDTO.builder()
                .userId(writer.getId())
                .username(writer.getUsername())
                .category(Category.POST)
                .targetName(String.valueOf(post.getId()))
                .build();
        activityService.makeAndSaveActivity(activityRequestDTO);
    }

    public List<Post> getPost(HttpServletRequest request) {
        String userId = tokenProvider.getUserId(request);
        List<FollowDTO> followings = followAPI.getFollowings(userId);
        List<Post> posts = postRepository.findByWriterId(userId);

        List<Post> updatedPosts = addPost(posts, followings);
        return sortPostByDesc(updatedPosts);
    }

    public List<Post> getFollowerPost(HttpServletRequest request) {
        String userId = tokenProvider.getUserId(request);
        List<FollowDTO> followers = followAPI.getFollowers(userId);
        List<Post> posts = new ArrayList<>();

        List<Post> updatedPosts = addPost(posts, followers);
        return sortPostByDesc(updatedPosts);
    }

    public void updatePost(Long id, Post post) {
        Optional<Post> originalPost = postRepository.findById(id);
        originalPost.ifPresent(newPost -> {
            newPost.setTitle(post.getTitle());
            newPost.setContent(post.getContent());

            postRepository.save(newPost);
        });
    }

    public void deletePost(HttpServletRequest request, final Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new NoSuchElementException("post not found"));
        postRepository.delete(post);
    }

    private List<Post> addPost(List<Post> posts, List<FollowDTO> targets) {
        List<Post> results = posts;
        for (FollowDTO target : targets) {
            User user = userRepository.findByUsername(target.getUsername());
            List<Post> targetPosts = postRepository.findByWriterId(user.getId());
            for (Post post : targetPosts) {
                results.add(post);
            }
        }
        return results;
    }

    private List<Post> sortPostByDesc(List<Post> posts) {
        List<Post> sortedPosts = posts.stream()
                .sorted(Comparator.comparing(Post::getCreatedAt).reversed())
                .collect(Collectors.toList());

        return sortedPosts;
    }
}
