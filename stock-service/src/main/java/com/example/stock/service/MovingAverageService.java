package com.example.stock.service;

import com.example.stock.dto.ClosePriceDTO;
import com.example.stock.model.DailyStock;
import com.example.stock.model.MovingAverage;
import com.example.stock.repository.DailyStockRepository;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovingAverageService {
    @Autowired
    private DailyStockRepository dailyStockRepository;

    @Cacheable(value = "movingAverageCache", key = "{#ticker, #date, #days}", unless = "#result == null")
    public Double getMovingAverage(String ticker, String date, int days) {
        double calculatedMovingAverage = calculateMovingAverage(ticker, date, days);
        return calculatedMovingAverage;
    }

    private Double calculateMovingAverage(String ticker, String date, int days) {
        List<ClosePriceDTO> closePriceDTOs = getDailyStocksFromDatabase(ticker, date, days);

        double sumOfClosePrices = closePriceDTOs.stream()
                .mapToDouble(ClosePriceDTO::getClose)
                .sum();

        double movingAverage = sumOfClosePrices / days;

        return movingAverage;
    }

    private List<ClosePriceDTO> getDailyStocksFromDatabase(String ticker, String date, int days) {
        LocalDate currentDate = LocalDate.parse(date);

        // Use Spring Data JPA repository method to fetch the required data
        Pageable pageable = PageRequest.of(0, days);  // Assuming you want 'days' number of records
        List<DailyStock> dailyStocks = dailyStockRepository.findByTickerAndDateBeforeOrderByDateDesc(ticker, currentDate, pageable);

        // Convert StockDaily entities to ClosePriceDTO (modify conversion logic as needed)
        List<ClosePriceDTO> closePriceDTOs = dailyStocks.stream()
                .map(stockDaily -> new ClosePriceDTO(ticker, stockDaily.getClose(), stockDaily.getDate()))
                .collect(Collectors.toList());

        return closePriceDTOs;
    }
}
