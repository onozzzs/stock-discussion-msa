package com.example.newsfeed.dto;

import com.example.newsfeed.model.Notification;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationResponseDTO {
    private String receiver;
    private String content;
    private LocalDateTime createdAt;

    public NotificationResponseDTO(Notification notification) {
        this.receiver = notification.getReceiver();
        this.content = notification.getContent();
        this.createdAt = notification.getCreatedAt();
    }
}
