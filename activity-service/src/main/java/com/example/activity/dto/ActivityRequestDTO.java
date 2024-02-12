package com.example.activity.dto;

import com.example.activity.model.Category;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ActivityRequestDTO {
    private String userId;
    private String username;
    private Category category;
    private String targetName;
}
