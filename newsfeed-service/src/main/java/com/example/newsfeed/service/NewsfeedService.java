package com.example.newsfeed.service;

import com.example.newsfeed.model.Newsfeed;
import com.example.newsfeed.repository.NewsfeedRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class NewsfeedService {
    @Autowired
    private NewsfeedRepository newsfeedRepository;

    @Autowired
    private TokenProvider tokenProvider;

    public List<Newsfeed> getNewsfeed(HttpServletRequest request) {
        String userId = tokenProvider.getUserId(request);
        return newsfeedRepository.findByUserId(userId);
    }
}
