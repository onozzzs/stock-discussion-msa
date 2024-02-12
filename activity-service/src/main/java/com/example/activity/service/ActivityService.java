package com.example.activity.service;

import com.example.activity.dto.ActivityRequestDTO;
import com.example.activity.model.Category;
import com.example.user.model.User;
import org.springframework.stereotype.Service;

@Service
public interface ActivityService {
    void makeAndSaveActivity(ActivityRequestDTO activityRequestDTO);
}
