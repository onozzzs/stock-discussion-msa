package com.example.newsfeed.controller;

import com.example.newsfeed.dto.NewsfeedResponseDTO;
import com.example.newsfeed.model.Newsfeed;
import com.example.newsfeed.service.NewsfeedService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/newsfeed")
public class NewsfeedController {
    @Autowired
    private NewsfeedService newsfeedService;

    @GetMapping
    public ResponseEntity<?> getNewsfeed(HttpServletRequest request) {
        List<Newsfeed> newsfeeds = newsfeedService.getNewsfeed(request);
        List<NewsfeedResponseDTO> responseDTOs = newsfeeds.stream().map(NewsfeedResponseDTO::new).collect(Collectors.toList());
        return ResponseEntity.ok().body(responseDTOs);
    }
}
