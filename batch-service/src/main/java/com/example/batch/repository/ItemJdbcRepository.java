package com.example.batch.repository;

import com.example.batch.model.DailyStock;
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
public class ItemJdbcRepository {

    private final JdbcTemplate jdbcTemplate;

    private int batchSize = 50000;

    public void saveAll(List<DailyStock> items) {
        int batchCount = 0;
        List<DailyStock> subItems = new ArrayList<>();
        for (int i = 0; i < items.size(); i++) {
            subItems.add(items.get(i));
            if ((i + 1) % batchSize == 0) {
                log.info(String.valueOf(subItems.size()) + " " + i);
                log.info(subItems.get(0).getTicker());
                batchCount = batchInsert(batchSize, batchCount, subItems);
            }
        }
        if (!subItems.isEmpty()) {
            batchCount = batchInsert(batchSize, batchCount, subItems);
        }
        System.out.println("batchCount: " + batchCount);
    }

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
}
