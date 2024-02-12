package com.example.activity.service;

import com.example.activity.dto.ActivityRequestDTO;
import com.example.activity.model.Activity;
import com.example.activity.repository.ActivityRepository;
import com.example.activity.api.NotificationAPI;
import com.example.activity.dto.NotificationRequestDTO;
import com.example.activity.model.Category;
import com.example.user.model.User;
import com.example.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FollowActivityServiceImpl implements ActivityService {
    @Autowired
    private ActivityRepository activityRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private NotificationAPI notificationAPI;

    @Override
    public void makeAndSaveActivity(ActivityRequestDTO activityRequestDTO) {
        String userId = activityRequestDTO.getUserId();
        String username = activityRequestDTO.getUsername();
        //        User user = userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException());

        String targetName = activityRequestDTO.getTargetName();
        User targetUser = userRepository.findByUsername(targetName);

        Category category = activityRequestDTO.getCategory();
        String content = category.makeContent(username, targetName);
        Activity activity = Activity.builder()
                .userId(userId)
                .content(content)
                .category(category)
                .build();

        activityRepository.save(activity);

        NotificationRequestDTO notificationRequestDTO = NotificationRequestDTO.builder()
                .activityId(activity.getId())
                .content(content)
                .isRead(false)
                .status(true)
                .receiver(targetUser.getId())
                .createdAt(activity.getCreatedAt())
                .build();
        notificationAPI.saveNotification(notificationRequestDTO);
    }
}
