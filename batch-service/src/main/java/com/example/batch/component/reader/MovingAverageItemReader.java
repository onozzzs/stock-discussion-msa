package com.example.batch.component.reader;

import com.example.batch.model.DailyStock;
import com.example.batch.model.Stock;
import com.example.batch.repository.DailyStockRepository;
import com.example.batch.repository.StockRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class MovingAverageItemReader implements ItemReader<List<DailyStock>> {

    private List<DailyStock> dailyStocks;
    private List<Stock> stocks;

    @Autowired
    private DailyStockRepository dailyStockRepository;

    @Autowired
    private StockRepository stockRepository;


    @Override
    public List<DailyStock> read() throws Exception {
        return null;
    }
}
