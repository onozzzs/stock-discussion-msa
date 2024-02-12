package com.example.activity.service;

import com.example.activity.api.NotificationAPI;
import com.example.activity.dto.ActivityRequestDTO;
import com.example.activity.dto.NotificationRequestDTO;
import com.example.activity.model.Category;
import com.example.activity.model.Activity;
import com.example.activity.model.Post;
import com.example.activity.repository.ActivityRepository;
import com.example.activity.repository.PostRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.NoSuchElementException;

@Slf4j
@Service
public class PostActivityServiceImpl implements ActivityService {
    @Autowired
    ActivityRepository activityRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    NotificationAPI notificationAPI;

    @Override
    public void makeAndSaveActivity(ActivityRequestDTO activityRequestDTO) {
        String userId = activityRequestDTO.getUserId();
        String username = activityRequestDTO.getUsername();
        Long postId = Long.parseLong(activityRequestDTO.getTargetName());
        Post post = postRepository.findById(postId).orElseThrow(() -> new NoSuchElementException("post not found"));
        String targetName = post.getTitle();

        Category category = activityRequestDTO.getCategory();
        String content = category.makeContent(username, targetName);

        Activity activity = Activity.builder()
                .userId(activityRequestDTO.getUserId())
                .content(content)
                .postId(postId)
                .category(category)
                .build();

        activityRepository.save(activity);
        if (post.getWriter().getId() != userId) {
            NotificationRequestDTO notificationDTO = NotificationRequestDTO.builder()
                    .receiver(post.getWriter().getId())
                    .activityId(activity.getId())
                    .content(content)
                    .createdAt(activity.getCreatedAt())
                    .status(true)
                    .build();
            notificationAPI.saveNotification(notificationDTO);
        }
    }
}
