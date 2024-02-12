package com.example.newsfeed.dto;

import com.example.newsfeed.model.Notification;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class NotificationRequestDTO {
    private String receiver;
    private String content;
    private Long activityId;
    private boolean status;
    private boolean isRead;
    private LocalDateTime createdAt;

    public static Notification toEntity(NotificationRequestDTO notificationRequestDTO) {
        return Notification.builder()
                .receiver(notificationRequestDTO.getReceiver())
                .activityId(notificationRequestDTO.getActivityId())
                .content(notificationRequestDTO.getContent())
                .createdAt(notificationRequestDTO.getCreatedAt())
                .status(notificationRequestDTO.isStatus())
                .isRead(notificationRequestDTO.isRead())
                .build();
    }
}
