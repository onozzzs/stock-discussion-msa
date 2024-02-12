package com.example.user.dto;

import com.example.user.model.Category;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ActivityRequestDTO {
    String userId;
    String username;
    Category category;
    String targetName;
}
