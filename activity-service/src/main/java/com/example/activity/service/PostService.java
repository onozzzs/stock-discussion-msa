package com.example.activity.service;

import com.example.activity.api.FollowAPI;
import com.example.activity.api.UserAPI;
import com.example.activity.dto.*;
import com.example.activity.dto.post.PostRequestDTO;
import com.example.activity.dto.post.PostResponseDTO;
import com.example.activity.dto.post.PostUpdateRequestDTO;
import com.example.activity.model.Post;
import com.example.activity.repository.PostRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PostService {
    @Value("${spring.data.web.pageable.default-page-size}")
    private int PAGE_SIZE;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostActivityServiceImpl activityService;

    @Autowired
    private TokenProvider tokenProvider;

    @Autowired
    private FollowAPI followAPI;

    @Autowired
    private UserAPI userAPI;

    public void savePost(HttpServletRequest request, PostRequestDTO requestDTO) {
        String userId = tokenProvider.getUserId(request);
        UserDTO userDTO = userAPI.getUserByUserId(userId);

        Post post = Post.toEntity(requestDTO);
        post.setUserId(userId);
        post.setUsername(userDTO.getUsername());

        postRepository.save(post);

//        ActivityRequestDTO activityRequestDTO = ActivityRequestDTO.builder()
//                .userId(userId)
//                .username(writer.getUsername())
//                .category(Category.POST)
//                .targetName(String.valueOf(post.getId()))
//                .build();
//        activityService.makeAndSaveActivity(activityRequestDTO);
    }

    public Page<PostResponseDTO> retrieveBoard(Pageable pageable, String ticker) {
        Page<Post> posts = postRepository.findByTicker(pageable, ticker);
        Page<PostResponseDTO> postResponseDTOs = posts.map(PostResponseDTO::new);

        return postResponseDTOs;
    }

    public Page<PostResponseDTO> getMyPost(HttpServletRequest request, Pageable pageable) {
        String userId = tokenProvider.getUserId(request);

        Page<Post> postPage = postRepository.findByUserId(pageable, userId);
        Page<PostResponseDTO> postResponseDTOs = postPage.map(PostResponseDTO::new);
        return postResponseDTOs;
    }

    public Page<PostResponseDTO> getAllPost(HttpServletRequest request) {
        String userId = tokenProvider.getUserId(request);

        List<FollowDTO> followings = followAPI.getFollowings(userId);

        List<Post> posts = postRepository.findByUserId(userId);
        List<Post> allPosts = addPost(posts, followings);
        List<Post> sortedPosts = sortPostByDesc(allPosts);

        List<PostResponseDTO> postResponseDTOs = sortedPosts.stream().map(PostResponseDTO::new).collect(Collectors.toList());

        return convertListToPage(postResponseDTOs, 0);
    }

    public void updatePost(Long id, PostUpdateRequestDTO requestDTO) {
        Optional<Post> originalPost = postRepository.findById(id);
        originalPost.ifPresent(newPost -> {
            newPost.setTitle(requestDTO.getTitle());
            newPost.setContent(requestDTO.getContent());

            postRepository.save(newPost);
        });
    }

    public void deletePost(final Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new NoSuchElementException("post not found"));
        postRepository.delete(post);
    }

    public Page<PostResponseDTO> search(SearchDTO searchDTO) {
        String type = searchDTO.getType();
        String keyword = searchDTO.getKeyword();

        List<Post> posts = getPostByType(type, keyword);
        List<PostResponseDTO> postResponseDTOs = posts.stream().map(PostResponseDTO::new).collect(Collectors.toList());

        return convertListToPage(postResponseDTOs, searchDTO.getPage());
    }

    private List<Post> getPostByType(String type, String keyword) {
        switch (type) {
            case "username":
                UserDTO userDTO = userAPI.getUserByUsername(keyword);
                return postRepository.findByUserId(userDTO.getId());
            case "title":
                return postRepository.findByTitleContaining(keyword);
            case "content":
                return postRepository.findByContentContaining(keyword);
            default:
                throw new RuntimeException("invalid type");
        }
    }

    private Page<PostResponseDTO> convertListToPage(List<PostResponseDTO> content, int pageNumber) {
        PageRequest pageRequest = PageRequest.of(pageNumber, PAGE_SIZE);

        return new PageImpl<>(content, pageRequest, content.size());
    }

    private List<Post> addPost(List<Post> posts, List<FollowDTO> followings) {
        List<Post> results = posts;
        for (FollowDTO follow : followings) {
            List<Post> followingPosts = postRepository.findByUserId(follow.getUserId());
            for (Post post : followingPosts) {
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
