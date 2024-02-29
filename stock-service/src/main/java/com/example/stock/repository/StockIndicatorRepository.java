package com.example.stock.repository;

import com.example.stock.model.StockIndicator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockIndicatorRepository extends JpaRepository<StockIndicator, Long> {
    Page<StockIndicator> findByTicker(Pageable pageable, String ticker);
}
