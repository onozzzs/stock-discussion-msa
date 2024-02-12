package com.example.newsfeed.repository;

import com.example.newsfeed.model.Newsfeed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewsfeedRepository extends JpaRepository<Newsfeed, Long> {
    List<Newsfeed> findByUserId(String userId);
}
