package com.example.activity.config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QueryDslConfig {
    @Autowired
    private EntityManager entityManager;

    @Bean
    JPAQueryFactory jpaQueryMethodFactory() {
        return new JPAQueryFactory(entityManager);
    }
}
