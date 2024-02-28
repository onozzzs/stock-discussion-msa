package com.example.batch.stockIndicator;

import com.example.batch.model.DailyStock;
import com.example.batch.model.Stock;
import com.example.batch.model.StockIndicator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class StockIndicatorProcessor implements ItemProcessor<List<DailyStock>, List<StockIndicator>> {

    @Override
    public List<StockIndicator> process(List<DailyStock> item) throws Exception {
        log.info("stockIndicatorProcessor item.size " + item.size());
        List<StockIndicator> stockIndicators = IndicatorCalculator.calculateMovingAverages(item);
        log.info("stockIndicatorProcessor " + stockIndicators.size());
        return stockIndicators;
    }
}
