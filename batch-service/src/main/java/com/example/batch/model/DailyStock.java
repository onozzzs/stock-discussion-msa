package com.example.batch.model;

import com.example.batch.dto.StockDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(
        indexes = {
                @Index(name = "daily_stock_index", columnList = "ticker, date")
        }
)
public class DailyStock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String ticker;

    private long open;

    private long close;

    private long high;

    private long low;

    private long volume;

    private LocalDate date;

    public DailyStock(StockDTO stockDTO) {
        this.open = stockDTO.getOpen();
        this.close = stockDTO.getClose();
        this.high = stockDTO.getHigh();
        this.low = stockDTO.getLow();
        this.volume = stockDTO.getVolume();
        this.ticker = stockDTO.getTicker();
        this.date = convertStringToLocalDateTime(stockDTO.getDate());
    }

    public DailyStock(String ticker, long open, long close, long high, long low, long volume, LocalDate date) {
        this.ticker = ticker;
        this.open = open;
        this.close = close;
        this.high = high;
        this.low = low;
        this.volume = volume;
        this.date = date;
    }

    private LocalDate convertStringToLocalDateTime(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(dateString, formatter);

        return localDate;
    }
}
