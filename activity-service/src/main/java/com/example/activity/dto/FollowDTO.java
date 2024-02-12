package com.example.activity.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FollowDTO {
    private String userId;
    private String username;
}
