package com.example.stock.repository;

import com.example.stock.model.DailyStock;
import org.springframework.cglib.core.Local;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DailyStockRepository extends JpaRepository<DailyStock, Long> {
    List<DailyStock> findByTicker(String ticker);

    LocalDate findEarliestDateByTicker(String ticker);

    List<DailyStock> findByTickerAndDateBetween(String ticker, LocalDate startDate, LocalDate endDate);
}
