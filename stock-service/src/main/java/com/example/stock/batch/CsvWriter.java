package com.example.stock.batch;

import com.example.stock.model.DailyStock;
import com.example.stock.repository.BatchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Configuration;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class CsvWriter implements ItemWriter<DailyStock> {
    private final BatchRepository batchRepository;

    @Override
    public void write(Chunk<? extends DailyStock> chunk) throws Exception {
        batchRepository.saveAll((List<DailyStock>) chunk.getItems(), "daily_stock");
    }
}
