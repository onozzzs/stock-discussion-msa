package com.example.user.controller;

import com.example.user.dto.FollowDTO;
import com.example.user.dto.FollowerDTO;
import com.example.user.dto.FollowingDTO;
import com.example.user.model.Follow;
import com.example.user.model.User;
import com.example.user.repository.FollowRepository;
import com.example.user.repository.UserRepository;
import com.example.user.service.FollowService;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/internal/user/follow")
public class FollowInternalController {
    @Autowired
    FollowService followService;

    @Autowired
    UserRepository userRepository;

    @GetMapping("/getFollowings")
    public List<FollowingDTO> getFollowings(@RequestParam(value = "userId") String userId) {
        return followService.getFollowingDTO(userId);
    }

    @GetMapping("/getFollowers")
    public List<FollowerDTO> getFollowers(@RequestParam(value = "userId") String userId) {
        return followService.getFollowerDTO(userId);
    }
}
