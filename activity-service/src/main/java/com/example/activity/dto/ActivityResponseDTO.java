package com.example.activity.dto;

import com.example.activity.model.Activity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ActivityResponseDTO {
    private Long id;
    private String userId;
    private String content;
    private LocalDateTime createdAt;

    public ActivityResponseDTO(Activity activity) {
        this.id = activity.getId();
        this.userId = activity.getUserId();
        this.content = activity.getContent();
        this.createdAt = activity.getCreatedAt();
    }
}
