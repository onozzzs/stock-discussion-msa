package com.example.stock.repository;

import com.example.stock.model.DailyStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DailyStockRepository extends JpaRepository<DailyStock, Long> {
}
