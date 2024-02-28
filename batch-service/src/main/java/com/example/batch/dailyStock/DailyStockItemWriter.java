package com.example.batch.dailyStock;

import com.example.batch.model.DailyStock;
import com.example.batch.repository.StockJdbcRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DailyStockItemWriter implements ItemWriter<List<DailyStock>> {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private StockJdbcRepository stockJdbcRepository;

    @Override
    public void write(Chunk<? extends List<DailyStock>> chunk) throws Exception {
        for (List<DailyStock> dailyStockList : chunk) {
            stockJdbcRepository.saveAll(dailyStockList);
        }
    }
}
