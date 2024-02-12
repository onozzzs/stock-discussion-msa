package com.example.user.service;

import com.example.user.api.ActivityAPI;
import com.example.user.api.NotificationAPI;
import com.example.user.dto.ActivityRequestDTO;
import com.example.user.dto.FollowResponseDTO;
import com.example.user.model.Category;
import com.example.user.model.Follow;
import com.example.user.repository.FollowRepository;
import com.example.user.model.User;
import com.example.user.repository.UserRepository;
import com.example.user.security.TokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Slf4j
@Service
public class FollowService {
    @Autowired
    private FollowRepository followRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenProvider tokenProvider;

    @Autowired
    private ActivityAPI activityAPI;

    @Autowired
    private NotificationAPI notificationAPI;

    public void addFollowing(HttpServletRequest request, final String followingName) {
        String userId = getUserId(request);
        User follower = userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException("user not found"));
        validate(follower);

        User following = userRepository.findByUsername(followingName);
        validate(following);

        Optional<Follow> follow = followRepository.findByFollowerIdAndFollowingId(userId, following.getId());
        if (follow.isPresent()) {
            throw new RuntimeException("addFollowing " + "follow already exists");
        }

        Follow newFollow = Follow.builder()
                .following(following)
                .follower(follower)
                .build();

        followRepository.save(newFollow);
        makeAndSaveFollowActivity(userId, follower.getUsername(), Category.FOLLOW, following.getUsername());
    }

    public List<String> getFollowings(HttpServletRequest request) {
        String userId = getUserId(request);
        User user = userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException("user not found"));
        List<String> followings = new ArrayList<>();
        for (Follow follow : user.getFollowingList()) {
            followings.add(follow.getFollowing().getUsername());
        }
        return followings;
    }

    public List<FollowResponseDTO> getFollowingDTO(String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException("user not found"));
        List<FollowResponseDTO> followings = new ArrayList<>();
        for (Follow follow : user.getFollowingList()) {
            FollowResponseDTO followingResponseDTO = FollowResponseDTO.builder()
                    .userId(follow.getFollowing().getId())
                    .username(follow.getFollowing().getUsername())
                    .build();
            followings.add(followingResponseDTO);
        }
        return followings;
    }

    public List<String> getFollowers(HttpServletRequest request) {
        String userId = getUserId(request);
        User user = userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException("user not found"));
        List<String> followers = new ArrayList<>();
        for (Follow follow : user.getFollowerList()) {
            followers.add(follow.getFollower().getUsername());
        }
        return followers;
    }

    public List<FollowResponseDTO> getFollowerDTO(final String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException("user not found"));
        List<FollowResponseDTO> followers = new ArrayList<>();
        for (Follow follow : user.getFollowerList()) {
            FollowResponseDTO followResponseDTO = FollowResponseDTO.builder()
                    .userId(follow.getFollower().getId())
                    .username(follow.getFollower().getUsername())
                    .build();
            followers.add(followResponseDTO);
        }
        return followers;
    }

    private String getUserId(HttpServletRequest request) {
        String token = tokenProvider.resolveAccessToken(request);
        return tokenProvider.validateAndGetUserId(token);
    }

    private void makeAndSaveFollowActivity(String userId, String username, Category category, String targetName) {
        ActivityRequestDTO activityRequestDTO = ActivityRequestDTO.builder()
                .userId(userId)
                .username(username)
                .category(category)
                .targetName(targetName)
                .build();
        activityAPI.updateActivity(activityRequestDTO);
    }

    private void validate(User user) {
        if (user == null) {
            log.error("follingUser cannot be null");
            throw new RuntimeException("addFollow: following is null");
        }
    }
}
