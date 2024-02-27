package com.example.stock.repository;

import com.example.stock.model.DailyStock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface DailyStockRepository extends JpaRepository<DailyStock, Long> {
    LocalDate findEarliestDateByTicker(String ticker);
    DailyStock findByTicker(String ticker);
    Optional<DailyStock> findTopByTickerOrderByDateDesc(String ticker);
    List<DailyStock> findByTickerAndDateBetween(String ticker, LocalDate startDate, LocalDate endDate);

    @Query("SELECT ds FROM DailyStock ds " +
            "WHERE (ds.ticker, ds.date) IN (" +
            "SELECT ds2.ticker, MAX(ds2.date) FROM DailyStock ds2 GROUP BY ds2.ticker)")
    Page<DailyStock> findLatestByName(Pageable pageable);
}
