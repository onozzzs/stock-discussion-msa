package com.example.stock.dto;

import com.example.stock.model.DailyStock;
import com.example.stock.model.Stock;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class StockDTO {
    private LocalDate date;
    private Long open;
    private Long high;
    private Long low;
    private Long close;
    private double volume;
    private String ticker;
    private String stockName;
    private String market;

    public StockDTO(DailyStock stock) {
        this.ticker = stock.getTicker();
        this.date = stock.getDate();
        this.open = stock.getOpen();
        this.high = stock.getHigh();
        this.low = stock.getLow();
        this.close = stock.getClose();
        this.volume = stock.getVolume();
    }
}
