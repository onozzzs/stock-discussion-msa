package com.example.stock.service;

import com.example.stock.dto.ClosePriceDTO;
import com.example.stock.model.DailyStock;
import com.example.stock.repository.DailyStockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovingAverageService {
    private IndicatorCalculator movingAverageCalculator;

    @Autowired
    private DailyStockRepository dailyStockRepository;

    @Cacheable(value = "indicatorCache", key = "{#ticker, #date, #days, #indicatorType}", unless = "#result == null")
    public Double calculateIndicator(String ticker, String date, int days, String indicatorType) {
        List<ClosePriceDTO> stocks = getDailyStocksFromDatabase(ticker, date, days);

        IndicatorCalculator movingAverageCalculator = stockList -> {
            double sumOfClosePrices = stockList.stream()
                    .mapToDouble(ClosePriceDTO::getClose)
                    .sum();

            return sumOfClosePrices / days;
        };

        Double movingAverage = movingAverageCalculator.calculate(stocks);
        return movingAverage;
    }


//    @Cacheable(value = "movingAverageCache", key = "{#ticker, #date, #days}", unless = "#result == null")
//    public Double getMovingAverage(String ticker, String date, int days) {
//        double calculatedMovingAverage = calculateMovingAverage(ticker, date, days);
//        return calculatedMovingAverage;
//    }

    private Double calculateMovingAverage(String ticker, String date, int days) {
        List<ClosePriceDTO> closePriceDTOs = getDailyStocksFromDatabase(ticker, date, days);

        double sumOfClosePrices = closePriceDTOs.stream()
                .mapToDouble(ClosePriceDTO::getClose)
                .sum();

        return sumOfClosePrices / days;
    }

    private List<ClosePriceDTO> getDailyStocksFromDatabase(String ticker, String date, int days) {
        LocalDate requestedDate = LocalDate.parse(date);
        LocalDate startDate = requestedDate.minusDays(days - 1);

        List<DailyStock> stockDailyList = dailyStockRepository.findByTickerAndDateBetween(ticker, startDate, requestedDate);

        List<ClosePriceDTO> closePriceDTOList = stockDailyList.stream()
                .map(stockDaily -> new ClosePriceDTO(ticker, stockDaily.getClose(), stockDaily.getDate()))
                .collect(Collectors.toList());

        return closePriceDTOList;
    }
}
