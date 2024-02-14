package com.example.stock.config;

import com.example.stock.model.Stock;
import com.example.stock.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class CsvWriter implements ItemWriter<Stock> {
    private final StockRepository stockRepository;

    @Override
    public void write(Chunk<? extends Stock> chunk) throws Exception {
        stockRepository.saveAll((List<Stock>) chunk.getItems());
    }
}
