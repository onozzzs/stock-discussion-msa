package com.example.stock.dto;

import com.example.stock.model.DailyStock;
import lombok.Data;
import java.time.LocalDate;

@Data
public class ChartDTO {
    private String ticker;
    private Long open;
    private Long close;
    private Long high;
    private Long low;
    private LocalDate date;

    public ChartDTO(DailyStock dailyStock) {
        this.ticker = dailyStock.getTicker();
        this.open = dailyStock.getOpen();
        this.close = dailyStock.getClose();
        this.high = dailyStock.getHigh();
        this.low = dailyStock.getLow();
        this.date = dailyStock.getDate();
    }
}
