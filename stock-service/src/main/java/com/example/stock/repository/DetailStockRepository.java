package com.example.stock.repository;

import com.example.stock.model.DetailStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DetailStockRepository extends JpaRepository<DetailStock, Long> {
    List<DetailStock> findTop4ByTickerOrderByDateDesc(String ticker);
}
