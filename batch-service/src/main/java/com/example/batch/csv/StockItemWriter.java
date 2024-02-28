package com.example.batch.csv;

import com.example.batch.model.Stock;
import com.example.batch.repository.StockJdbcRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class StockItemWriter implements ItemWriter<Stock> {
    private final StockJdbcRepository stockJdbcRepository;

    @Override
    public void write(Chunk<? extends Stock> chunk) throws Exception {
        stockJdbcRepository.saveStock((List<Stock>) chunk.getItems(), "stock");
    }
}
