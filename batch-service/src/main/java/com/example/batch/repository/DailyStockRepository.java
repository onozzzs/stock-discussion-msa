package com.example.batch.repository;

import com.example.batch.model.DailyStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DailyStockRepository extends JpaRepository<DailyStock, Long> {
}
