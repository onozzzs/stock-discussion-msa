package com.example.stock.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class StockDTO {
    private int index;
    private String date;
    private Long open;
    private Long high;
    private Long low;
    private Long close;
    private Long volume;
    private Double fluctuationRate;
    private String ticker;
    private String stock_name;
    private String market;
}
