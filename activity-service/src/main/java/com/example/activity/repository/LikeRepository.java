package com.example.activity.repository;

import com.example.activity.model.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
    Optional<Like> findByUserIdAndPostId(String userId, Long postId);
    Optional<Like> findByUserIdAndCommentId(String userId, Long commentId);
}
