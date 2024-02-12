package com.example.user.controller;

import com.example.user.service.FollowService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/user/follow")
public class FollowController {
    @Autowired
    FollowService followService;

    @PostMapping
    public ResponseEntity<?> follow(@RequestParam("following") String following, HttpServletRequest request) {
        followService.addFollowing(request, following);
        return ResponseEntity.ok().body(following + " 을 팔로우");
    }

    @GetMapping("/followings")
    public ResponseEntity<?> getFollowings(HttpServletRequest request) {
        List<String> followings = followService.getFollowings(request);
        return ResponseEntity.ok().body(followings);
    }

    @GetMapping("/followers")
    public ResponseEntity<?> getFollowers(HttpServletRequest request) {
        List<String> followers = followService.getFollowers(request);
        return ResponseEntity.ok().body(followers);
    }
}
