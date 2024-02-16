package com.example.stock.repository;

import com.example.stock.model.DailyStock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DailyStockRepository extends JpaRepository<DailyStock, Long> {
    Page<DailyStock> findByMarketContaining(Pageable pageable, String keyword);
    Page<DailyStock> findByTickerContaining(Pageable pageable, String keyword);
    Page<DailyStock> findByStockNameContaining(Pageable pageable, String keyword);
}
