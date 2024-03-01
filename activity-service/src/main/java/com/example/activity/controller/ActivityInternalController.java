package com.example.activity.controller;

import com.example.activity.dto.FollowDTO;
import com.example.activity.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/internal/activity")
public class ActivityInternalController {
    @Autowired
    ActivityService activityService;

    @PostMapping("/saveActivity")
    public void saveActivity(@RequestBody FollowDTO followDTO) {
        activityService.saveFollowActivity(followDTO);
    }
}
