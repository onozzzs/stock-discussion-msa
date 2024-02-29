package com.example.stock.dto;

import com.example.stock.model.StockIndicator;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class MovingAverageDTO {
    private String ticker;
    private long price_12;
    private long price_20;
    private long price_26;
    private LocalDate date;

    public MovingAverageDTO(StockIndicator stockIndicator) {
        this.ticker = stockIndicator.getTicker();
        this.price_12 = stockIndicator.getPrice_12();
        this.price_20 = stockIndicator.getPrice_20();
        this.price_26 = stockIndicator.getPrice_26();
        this.date = stockIndicator.getDate();
    }
}
