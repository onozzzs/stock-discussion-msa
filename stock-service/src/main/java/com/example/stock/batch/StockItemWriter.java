package com.example.stock.batch;

import com.example.stock.model.DetailStock;
import com.example.stock.model.Stock;
import com.example.stock.repository.BatchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class StockItemWriter implements ItemWriter<Stock> {
    private final BatchRepository batchRepository;

    @Override
    public void write(Chunk<? extends Stock> chunk) throws Exception {
        batchRepository.saveStock((List<Stock>) chunk.getItems(), "stock");
    }
}