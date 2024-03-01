package com.example.activity.service;

import com.example.activity.api.NotificationAPI;
import com.example.activity.dto.FollowDTO;
import com.example.activity.model.Activity;
import com.example.activity.model.Category;
import com.example.activity.model.Comment;
import com.example.activity.model.Post;
import com.example.activity.repository.ActivityRepository;
import com.example.activity.repository.PostRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ActivityService {
    @Autowired
    ActivityRepository activityRepository;

    @Autowired
    NotificationAPI notificationAPI;

    public void saveCommentActivity(Comment comment) {
        Activity activity = Activity.builder()
                .commentId(comment.getCommentId())
                .category(Category.COMMENT)
                .build();

        activityRepository.save(activity);
    }

    public void savePostActivity(Post post) {
        Activity activity = Activity.builder()
                .postId(post.getId())
                .category(Category.POST)
                .build();

        activityRepository.save(activity);
    }

    public void saveFollowActivity(FollowDTO followDTO) {
        Activity activity = Activity.builder()
                .followId(followDTO.getId())
                .category(Category.FOLLOW)
                .build();

        activityRepository.save(activity);
    }
}
