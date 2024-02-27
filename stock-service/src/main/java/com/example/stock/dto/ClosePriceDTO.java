package com.example.stock.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class ClosePriceDTO {
    private String ticker;
    private long close;
    private LocalDate date;
}
