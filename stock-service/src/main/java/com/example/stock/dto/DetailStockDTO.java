package com.example.stock.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DetailStockDTO {
    private String ticker;

    private Long bps;

    private Double per;

    private Double pbr;

    private Long eps;

    private Double divRate;

    private Long dps;

    private String date;
}
