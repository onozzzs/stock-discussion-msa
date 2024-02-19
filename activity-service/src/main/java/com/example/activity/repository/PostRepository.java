package com.example.activity.repository;

import com.example.activity.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>{
    Page<Post> findByTicker(Pageable pageable, String ticker);
    Page<Post> findByUsername(Pageable pageable, String username);
    Page<Post> findByTitleContaining(Pageable pageable, String title);
    Page<Post> findByContentContaining(Pageable pageable, String content);

    List<Post> findByUserId(String userId);
    Optional<Post> findById(Long postId);
}
