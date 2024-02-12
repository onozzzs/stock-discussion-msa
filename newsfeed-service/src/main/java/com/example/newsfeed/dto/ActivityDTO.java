package com.example.newsfeed.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ActivityDTO {
    private Long id;
    private String userId;
    private String content;
    private LocalDateTime createdAt;
}
