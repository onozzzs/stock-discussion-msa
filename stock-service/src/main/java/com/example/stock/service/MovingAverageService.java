//package com.example.stock.service;
//
//import com.example.stock.model.DailyStock;
//import com.example.stock.repository.DailyStockRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cache.annotation.Cacheable;
//import org.springframework.stereotype.Service;
//import java.time.LocalDate;
//import java.util.List;
//
//@Service
//public class MovingAverageService {
//    @Autowired
//    private DailyStockRepository dailyStockRepository;
//
//    @Cacheable(value = "indicatorCache", key = "{#ticker, #date, #days, #indicatorType}", unless = "#result == null")
//    public Double calculateIndicator(String ticker, String date, int days, String indicatorType) {
//        List<DailyStock> stocks = getDailyStocksFromDatabase(ticker, date, days);
//
//        IndicatorCalculator movingAverageCalculator = stockList -> {
//            double sumOfClosePrices = stockList.stream()
//                    .mapToLong(DailyStock::getClose)
//                    .sum();
//
//            return sumOfClosePrices / days;
//        };
//
//        Double movingAverage = movingAverageCalculator.calculate(stocks);
//        return movingAverage;
//    }
//
//    private List<DailyStock> getDailyStocksFromDatabase(String ticker, String date, int days) {
//        LocalDate requestedDate = LocalDate.parse(date);
//        LocalDate startDate = requestedDate.minusDays(days - 1);
//
//        List<DailyStock> stockDailyList = dailyStockRepository.findByTickerAndDateBetween(ticker, startDate, requestedDate);
//
//        return stockDailyList;
//    }
//}
