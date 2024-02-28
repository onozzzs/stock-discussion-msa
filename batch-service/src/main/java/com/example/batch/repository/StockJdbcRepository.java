package com.example.batch.repository;

import com.example.batch.model.DailyStock;
import com.example.batch.model.DetailStock;
import com.example.batch.model.Stock;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class StockJdbcRepository {
    private final JdbcTemplate jdbcTemplate;

    private static int BATCH_SIZE = 50000;

    @Transactional
    public void deleteAll(String table) {
        String sql = "DELETE FROM " + table;
        jdbcTemplate.update(sql);
    }

    public void saveAll(List<DailyStock> items) {
        int batchCount = 0;
        List<DailyStock> subItems = new ArrayList<>();
        for (int i = 0; i < items.size(); i++) {
            subItems.add(items.get(i));
            if ((i + 1) % BATCH_SIZE == 0) {
                log.info(String.valueOf(subItems.size()) + " " + i);
                log.info(subItems.get(0).getTicker());
                batchCount = batchInsert(BATCH_SIZE, batchCount, subItems);
            }
        }
        if (!subItems.isEmpty()) {
            batchCount = batchInsert(BATCH_SIZE, batchCount, subItems);
        }
        System.out.println("batchCount: " + batchCount);
    }

    @Transactional
    private int batchInsert(int batchSize, int batchCount, List<DailyStock> subItems) {
        String sql = "INSERT INTO daily_stock (date, ticker, open, high, low, close, volume) VALUES (?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                DailyStock dailyStock = subItems.get(i);
                preparedStatement.setDate(1, Date.valueOf(dailyStock.getDate()));
                preparedStatement.setString(2, dailyStock.getTicker());
                preparedStatement.setLong(3, dailyStock.getOpen());
                preparedStatement.setLong(4, dailyStock.getHigh());
                preparedStatement.setLong(5, dailyStock.getLow());
                preparedStatement.setLong(6, dailyStock.getClose());
                preparedStatement.setDouble(7, dailyStock.getVolume());
            }

            @Override
            public int getBatchSize() {
                return subItems.size();
            }
        });
        subItems.clear();
        batchCount++;
        return batchCount;
    }

    @Transactional
    public void saveStock(List<Stock> stocks, String table) {
        String sql = "INSERT INTO " + table + "(stock_name, ticker, market) " + "VALUES (?, ?, ?) " +
                "ON DUPLICATE KEY UPDATE ticker = ticker";
        jdbcTemplate.batchUpdate(sql,
                stocks,
                500,
                (PreparedStatement ps, Stock stock) -> {
                    ps.setString(1, stock.getStockName());
                    ps.setString(2, stock.getTicker());
                    ps.setString(3, stock.getMarket());
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
