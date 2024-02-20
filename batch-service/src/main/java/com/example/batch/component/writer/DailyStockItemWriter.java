package com.example.batch.component.writer;

import com.example.batch.model.DailyStock;
import com.example.batch.repository.ItemJdbcRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;

import java.sql.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@Slf4j
@Component
public class DailyStockItemWriter implements ItemWriter<List<DailyStock>> {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ItemJdbcRepository itemJdbcRepository;

    @Override
    public void write(Chunk<? extends List<DailyStock>> chunk) throws Exception {
        for (List<DailyStock> dailyStockList : chunk) {
            log.info("chunk size " + chunk.size());
            itemJdbcRepository.saveAll(dailyStockList);
        }
    }

//    @Override
//    public void write(Chunk<? extends List<DailyStock>> chunk) throws Exception {
//        for (List<DailyStock> dailyStockList : chunk) {
//            batchUpdateDailyStock(dailyStockList);
//        }
//    }
//
//    @Transactional
//    private void batchUpdateDailyStock(List<DailyStock> dailyStockList) {
//        log.info(String.valueOf(dailyStockList.size()));
//        String sql = "INSERT INTO daily_stock (date, ticker, open, high, low, close, volume) VALUES (?, ?, ?, ?, ?, ?, ?)";
//        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
//            @Override
//            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
//                DailyStock dailyStock = dailyStockList.get(i);
//                preparedStatement.setDate(1, Date.valueOf(dailyStock.getDate()));
//                preparedStatement.setString(2, dailyStock.getTicker());
//                preparedStatement.setLong(3, dailyStock.getOpen());
//                preparedStatement.setLong(4, dailyStock.getHigh());
//                preparedStatement.setLong(5, dailyStock.getLow());
//                preparedStatement.setLong(6, dailyStock.getClose());
//                preparedStatement.setDouble(7, dailyStock.getVolume());
//            }
//
//            @Override
//            public int getBatchSize() {
//                return dailyStockList.size();
//            }
//        });
//    }
}
