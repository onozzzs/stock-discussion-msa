package com.example.activity.repository;

import com.example.activity.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>{
    List<Post> findByWriterId(String writer);
    Optional<Post> findById(Long postId);
}
