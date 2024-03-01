package com.example.activity.model;

import com.example.activity.dto.post.PostRequestDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Post extends BaseTimeEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String ticker;

    private String title;

    private String content;

    @ColumnDefault("0")
    private int likeCount;

    @ColumnDefault("0")
    private int commentCount;

    private String userId;

    private String username;

    public void updateLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public static Post toEntity(PostRequestDTO postRequestDTO) {
        return Post.builder()
                .title(postRequestDTO.getTitle())
                .ticker(postRequestDTO.getTicker())
                .content(postRequestDTO.getContent())
                .build();
    }
}
