package com.example.activity.dto;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public class NotificationResponseDTO {
    private String receiver;
    private String content;
    private Long activityId;
    private boolean status;
    private boolean isRead;
    private LocalDateTime createdAt;
}
