package com.example.activity.model;

import com.example.user.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Builder
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Post extends BaseTimeEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String ticker;

    private String userId;

    private String username;

    private String title;

    private String content;

    @ColumnDefault("0")
    private int likeCount;

    public void updateLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }
}
