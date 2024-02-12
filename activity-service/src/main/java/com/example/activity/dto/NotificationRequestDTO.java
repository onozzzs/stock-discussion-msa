package com.example.activity.dto;

import lombok.Builder;
import java.time.LocalDateTime;

@Builder
public class NotificationRequestDTO {
    private String receiver;
    private Long activityId;
    private String content;
    private LocalDateTime createdAt;
    private Boolean status;
    private Boolean isRead;
}
