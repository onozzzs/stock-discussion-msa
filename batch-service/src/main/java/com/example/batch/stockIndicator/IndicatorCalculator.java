package com.example.batch.stockIndicator;

import com.example.batch.model.DailyStock;
import com.example.batch.model.StockIndicator;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class IndicatorCalculator {
    private static List<StockIndicator> stockIndicators;

    public static List<StockIndicator> calculateMovingAverages(List<DailyStock> dailyStocks) {
        stockIndicators = initializeStockIndicators(dailyStocks);
        calculateMovingAverage(dailyStocks, 12);
        calculateMovingAverage(dailyStocks, 20);
        calculateMovingAverage(dailyStocks, 26);

        return stockIndicators;
    }

    public static void calculateMovingAverage(List<DailyStock> dailyStocks, int days) {
        long average = 0L;
        for (int i = days - 1; i < dailyStocks.size(); i++) {
            long sum = 0;
            for (int j = i - days + 1; j <= i; j++) {
                sum += dailyStocks.get(j).getClose();
            }

            average = sum / days;

            if (days == 12) {
                stockIndicators.get(i).setPrice_12(average);
            } else if (days == 20) {
                stockIndicators.get(i).setPrice_20(average);
            } else {
                stockIndicators.get(i).setPrice_26(average);
            }
        }
    }

    private static List<StockIndicator> initializeStockIndicators(List<DailyStock> dailyStocks) {
        stockIndicators = new ArrayList<>();
        for (DailyStock dailyStock : dailyStocks) {
            StockIndicator stockIndicator = new StockIndicator(dailyStock.getTicker(), dailyStock.getDate());
            stockIndicators.add(stockIndicator);
        }

        return stockIndicators;
    }
}
