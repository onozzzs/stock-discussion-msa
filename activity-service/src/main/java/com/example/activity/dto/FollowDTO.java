package com.example.activity.dto;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FollowDTO {
    private String userId;
    private String username;
}
