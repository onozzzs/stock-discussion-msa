package com.example.stock.model;

import com.example.stock.dto.StockDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Data
@Entity
public class Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String stockName;

    private String ticker;

    private Long open;

    private Long close;

    private Long high;

    private Long low;

    private Long volume;

    private Double fluctuationRate;

    private String market;

    private LocalDate date;

    public Stock(StockDTO stockDTO) {
        this.stockName = stockDTO.getStock_name();
        this.ticker = stockDTO.getTicker();
        this.open = stockDTO.getOpen();
        this.close = stockDTO.getClose();
        this.high = stockDTO.getHigh();
        this.low = stockDTO.getLow();
        this.volume = stockDTO.getVolume();
        this.fluctuationRate = stockDTO.getFluctuationRate();
        this.market = stockDTO.getMarket();
        this.date = convertStringToLocalDateTime(stockDTO.getDate());
    }

    private LocalDate convertStringToLocalDateTime(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(dateString, formatter);

        return localDate;
    }
}
