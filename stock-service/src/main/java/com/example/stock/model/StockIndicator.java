package com.example.stock.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Entity
@NoArgsConstructor
@Table(name = "stock_indicator", indexes = @Index(name = "idx_ticker", columnList = "ticker"))
public class StockIndicator {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String ticker;
    private long price_12;
    private long price_20;
    private long price_26;
    private LocalDate date;

    public StockIndicator(String ticker, LocalDate date) {
        this.ticker = ticker;
        this.date = date;
    }
}
