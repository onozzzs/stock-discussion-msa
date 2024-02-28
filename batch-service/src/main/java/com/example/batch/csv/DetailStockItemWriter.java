package com.example.batch.csv;

import com.example.batch.model.DetailStock;
import com.example.batch.repository.StockJdbcRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class DetailStockItemWriter implements ItemWriter<DetailStock> {
    private final StockJdbcRepository stockJdbcRepository;

    @Override
    public void write(Chunk<? extends DetailStock> chunk) throws Exception {
        stockJdbcRepository.saveDetails((List<DetailStock>) chunk.getItems(), "detail_stock");
    }
}
