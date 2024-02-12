package com.example.activity.service;

import com.example.activity.dto.ActivityResponseDTO;
import com.example.activity.dto.PostResponseDTO;
import com.example.activity.model.Activity;
import com.example.activity.repository.ActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ActivityGetService {
    @Autowired
    ActivityRepository activityRepository;

    public List<ActivityResponseDTO> getActivities(final String userId) {
        List<Activity> activities = activityRepository.findByUserId(userId);
        List<ActivityResponseDTO> activityResponseDTOs = activities.stream().map(ActivityResponseDTO::new).collect(Collectors.toList());
        return activityResponseDTOs;
    }
}
