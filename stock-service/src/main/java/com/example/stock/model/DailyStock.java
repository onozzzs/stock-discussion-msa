package com.example.stock.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
public class DailyStock {
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
}
