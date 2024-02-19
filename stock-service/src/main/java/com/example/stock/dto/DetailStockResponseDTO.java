package com.example.stock.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DetailStockResponseDTO {
    private String name;

    private String ticker;

    private Long bps;

    private Double per;

    private Double pbr;

    private Long eps;

    private Double divRate;

    private Long dps;

    private LocalDate date;
}
