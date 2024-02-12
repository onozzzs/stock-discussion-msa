package com.example.user.controller;

import com.example.user.dto.FollowResponseDTO;
import com.example.user.service.FollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/user/internal/follow")
public class FollowInternalController {
    @Autowired
    FollowService followService;

    @GetMapping("/getFollowings")
    public List<FollowResponseDTO> getFollowings(String userId) {
        return followService.getFollowingDTO(userId);
    }

    @GetMapping("/getFollowers")
    public List<FollowResponseDTO> getFollowers(String userId) {
        return followService.getFollowerDTO(userId);
    }
}
