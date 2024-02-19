package com.example.stock.repository;

import com.example.stock.model.DailyStockToday;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DailyStockTodayRepository extends JpaRepository<DailyStockToday, Long> {
    Page<DailyStockToday> findByMarketContaining(Pageable pageable, String keyword);
    Page<DailyStockToday> findByTickerContaining(Pageable pageable, String keyword);
    Page<DailyStockToday> findByStockNameContaining(Pageable pageable, String keyword);
}
