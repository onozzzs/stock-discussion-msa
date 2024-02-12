package com.example.newsfeed.dto;

import com.example.newsfeed.model.Notification;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationDTO {
    private String receiver;
    private Long activityId;
    private String content;
    private LocalDateTime createdAt;
    private Boolean status;

    public static Notification toEntity(NotificationDTO notificationDTO) {
        return Notification.builder()
                .activityId(notificationDTO.getActivityId())
                .content(notificationDTO.getContent())
                .createdAt(notificationDTO.getCreatedAt())
                .status(true)
                .build();
    }
}
