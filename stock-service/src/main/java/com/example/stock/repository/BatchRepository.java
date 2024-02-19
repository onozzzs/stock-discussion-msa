package com.example.stock.repository;

import com.example.stock.model.DailyStock;
import com.example.stock.model.DetailStock;
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
public class BatchRepository {
    private final JdbcTemplate jdbcTemplate;

    @Transactional
    public void deleteAll(String table) {
        String sql = "DELETE FROM " + table;
        jdbcTemplate.update(sql);
    }

    @Transactional
    public void saveStock(List<Stock> stocks, String table) {
        String sql = "INSERT INTO " + table + "(stock_name, ticker) " + "VALUES (?, ?)";
        jdbcTemplate.batchUpdate(sql,
                stocks,
                500,
                (PreparedStatement ps, Stock stock) -> {
                    ps.setString(1, stock.getStockName());
                    ps.setString(2, stock.getTicker());
                });
    }

    @Transactional
    public void saveAll(List<DailyStock> stocks, String table) {
        String sql = "INSERT INTO " + table + "(date, high, low, open, close, fluctuation_rate, volume, stock_name, ticker, market)" +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.batchUpdate(sql,
                stocks,
                2000,
                (PreparedStatement ps, DailyStock stock) -> {
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

    @Transactional
    public void saveDetails(List<DetailStock> stocks, String table) {
        String sql = "INSERT INTO " + table + "(date, bps, per, pbr, eps, div_rate, dps, ticker)" +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.batchUpdate(sql,
                stocks,
                2000,
                (PreparedStatement ps, DetailStock stock) -> {
                    ps.setDate(1, Date.valueOf(stock.getDate()));
                    ps.setLong(2, stock.getBps());
                    ps.setDouble(3, stock.getPer());
                    ps.setDouble(4, stock.getPbr());
                    ps.setLong(5, stock.getEps());
                    ps.setDouble(6, stock.getDivRate());
                    ps.setLong(7, stock.getDps());
                    ps.setString(8, stock.getTicker());
                });
    }
}
