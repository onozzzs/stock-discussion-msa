package com.example.newsfeed.service;

import com.example.newsfeed.api.ActivityAPI;
import com.example.newsfeed.api.FollowAPI;
import com.example.newsfeed.dto.ActivityDTO;
import com.example.newsfeed.dto.FollowDTO;
import com.example.newsfeed.dto.NotificationDTO;
import com.example.newsfeed.dto.NotificationRequestDTO;
import com.example.newsfeed.model.Notification;
import com.example.newsfeed.repository.NotificationRepository;
import com.netflix.discovery.converters.Auto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class NotificationService {
    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private FollowAPI followAPI;

    @Autowired
    private ActivityAPI activityAPI;

    @Autowired
    private TokenProvider tokenProvider;

    public List<Notification> getNotification(HttpServletRequest request) {
        String userId = tokenProvider.getUserId(request);
        updateFollowingsNotification(userId);
        return notificationRepository.findByReceiverAndStatus(userId, false);
    }

    public List<Notification> getNotificationToUser(HttpServletRequest request) {
        String userId = tokenProvider.getUserId(request);
        return notificationRepository.findByReceiverAndStatus(userId, true);
    }

    public void updateNotification(final String userId, NotificationDTO notificationDTO) {
        Notification notification = NotificationDTO.toEntity(notificationDTO);
        notification.setReceiver(userId);
        notification.setStatus(true);
        notificationRepository.save(notification);
    }

    public void save(NotificationRequestDTO notificationRequestDTO) {
        Notification notification = NotificationRequestDTO.toEntity(notificationRequestDTO);
        notificationRepository.save(notification);
    }

    private void updateFollowingsNotification(final String userId) {
        List<FollowDTO> followings = followAPI.getFollowings(userId);
//        List<String> followings = getFollowings(userId);
        for (FollowDTO following : followings) {
//            User followingUser = userRepository.findByUsername(followingUserName);
//            List<ActivityDTO> followingActivities = activityRepository.findByUserId(followingUser.getId());
            List<ActivityDTO> followingActivities = activityAPI.getActivities(following.getUserId());
            for (ActivityDTO activity : followingActivities) {
                Optional<Notification> notification = notificationRepository.findByReceiverAndActivityId(userId, activity.getId());
                if (notification.isPresent()) continue;

                Notification newNotification = Notification.builder()
                        .receiver(userId)
                        .activityId(activity.getId())
                        .content(activity.getContent())
                        .createdAt(activity.getCreatedAt())
                        .status(false)
                        .build();
                notificationRepository.save(newNotification);
            }
        }
    }
}
