package com.example.stock.batch;

import com.example.stock.model.DailyStock;
import com.example.stock.repository.BatchRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Configuration;
import java.util.List;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class DailyStockWriter implements ItemWriter<DailyStock> {
    private final BatchRepository batchRepository;

    @Override
    public void write(Chunk<? extends DailyStock> chunk) throws Exception {
        log.info("---------daily_stock--------------------------------");
        batchRepository.deleteAll("daily_stock_today");
        batchRepository.saveAll((List<DailyStock>) chunk.getItems(), "daily_stock_today");
    }
}
