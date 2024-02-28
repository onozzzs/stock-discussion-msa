package com.example.batch.repository;

import com.example.batch.model.StockIndicator;
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
public class StockIndicatorJdbcRepository {
    private final JdbcTemplate jdbcTemplate;
    private static int BATCH_SIZE = 20000;

    public void saveAll(List<StockIndicator> items) {
        int batchCount = 0;
        List<StockIndicator> subItems = new ArrayList<>();
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
    private int batchInsert(int batchSize, int batchCount, List<StockIndicator> stockIndicators) {
        String sql = "INSERT INTO stock_indicator (ticker, price_12, price_20, price_26, date) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                StockIndicator stockIndicator = stockIndicators.get(i);
                preparedStatement.setString(1, stockIndicator.getTicker());
                preparedStatement.setLong(2, stockIndicator.getPrice_12());
                preparedStatement.setLong(3, stockIndicator.getPrice_20());
                preparedStatement.setLong(4, stockIndicator.getPrice_26());
                preparedStatement.setDate(5, Date.valueOf(stockIndicator.getDate()));
            }

            @Override
            public int getBatchSize() {
                return stockIndicators.size();
            }
        });
        stockIndicators.clear();
        batchCount++;
        return batchCount;
    }
}