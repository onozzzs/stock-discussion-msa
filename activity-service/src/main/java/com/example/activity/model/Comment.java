package com.example.activity.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Comment extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long commentId;

    private String userId;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "parent_id")
    private Comment parent;

    @OneToMany(mappedBy = "parent", orphanRemoval = true)
    private List<Comment> children = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    private String content;

    @Column(columnDefinition = "boolean default false")
    private Boolean isDeleted;

    @ColumnDefault("0")
    private int likeCount;

    public void updateUserId(String userId) {
        this.userId = userId;
    }

    public void updatePost(Post post) {
        this.post = post;
    }

    public void updateParent(Comment parent) {
        this.parent = parent;
    }

    public void updateIsDeleted(Boolean isDeleted) {
        this.isDeleted = (isDeleted != null) ? isDeleted : false;
    }

    public void updateLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }
}
