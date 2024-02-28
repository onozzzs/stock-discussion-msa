package com.example.batch.stockIndicator;

import com.example.batch.model.StockIndicator;
import com.example.batch.repository.StockIndicatorJdbcRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class StockIndicatorWriter implements ItemWriter<List<StockIndicator>> {
    private final StockIndicatorJdbcRepository stockIndicatorJdbcRepository;

    @Override
    public void write(Chunk<? extends List<StockIndicator>> chunk) throws Exception {
        log.info("stockIndicatorWriter " + chunk.size());
        List<StockIndicator> stockIndicators = new ArrayList<>();
        for (List<StockIndicator> subList : chunk.getItems()) {
            stockIndicators.addAll(subList);
        }

        stockIndicatorJdbcRepository.saveAll(stockIndicators);
    }
}
