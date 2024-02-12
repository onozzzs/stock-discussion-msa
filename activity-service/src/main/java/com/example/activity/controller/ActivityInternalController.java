package com.example.activity.controller;

import com.example.activity.dto.ActivityRequestDTO;
import com.example.activity.dto.ActivityResponseDTO;
import com.example.activity.service.ActivityGetService;
import com.example.activity.service.FollowActivityServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/activity/internal")
public class ActivityInternalController {
    @Autowired
    ActivityGetService activityService;

    @Autowired
    FollowActivityServiceImpl followActivityService;

    @GetMapping("/getActivities")
    public List<ActivityResponseDTO> getActivities(String userId) {
        return activityService.getActivities(userId);
    }

    @PostMapping("/updateActivity")
    public void makeAndSaveActivity(@RequestBody ActivityRequestDTO activityRequestDTO) {
        followActivityService.makeAndSaveActivity(activityRequestDTO);
    }
}
