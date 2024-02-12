package com.example.newsfeed.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;

@Builder
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="notification_id")
    private Long id;

    private String receiver;

    private Long activityId;

    private String content;

    private LocalDateTime createdAt;

    @ColumnDefault("false")
    private Boolean status;
    @ColumnDefault("false")
    private Boolean isRead;
}
