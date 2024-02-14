package com.example.stock.repository;

import com.example.stock.model.Stock;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class StockRepository {
    private final JdbcTemplate jdbcTemplate;

    @Transactional
    public void saveAll(List<Stock> stocks) {
        String sql = "INSERT INTO stock (date, high, low, open, close, fluctuation_rate, volume, stock_name, ticker, market)" +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.batchUpdate(sql,
                stocks,
                2000,
                (PreparedStatement ps, Stock stock) -> {
                    ps.setDate(1, Date.valueOf(stock.getDate()));
                    ps.setLong(2, stock.getHigh());
                    ps.setLong(3, stock.getLow());
                    ps.setLong(4, stock.getOpen());
                    ps.setLong(5, stock.getClose());
                    ps.setDouble(6, stock.getFluctuationRate());
                    ps.setLong(7, stock.getVolume());
                    ps.setString(8, stock.getStockName());
                    ps.setString(9, stock.getTicker());
                    ps.setString(10, stock.getMarket());
                });
    }
}
