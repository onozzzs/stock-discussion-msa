package com.example.stock.batch;

import com.example.stock.model.DetailStock;
import com.example.stock.repository.BatchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class DetailStockItemWriter implements ItemWriter<DetailStock> {
    private final BatchRepository batchRepository;

    @Override
    public void write(Chunk<? extends DetailStock> chunk) throws Exception {
        batchRepository.saveDetails((List<DetailStock>) chunk.getItems(), "detail_stock");
    }
}
