package com.example.stock.model;

import com.example.stock.dto.DetailStockDTO;
import com.example.stock.dto.DetailStockResponseDTO;
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
public class DetailStock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String ticker;

    private Long bps;

    private Double per;

    private Double pbr;

    private Long eps;

    private Double divRate;

    private Long dps;

    private LocalDate date;

    public DetailStock(DetailStockDTO detailStockDTO) {
        this.ticker = detailStockDTO.getTicker();
        this.bps = detailStockDTO.getBps();
        this.per = detailStockDTO.getPer();
        this.pbr = detailStockDTO.getPbr();
        this.eps = detailStockDTO.getEps();
        this.divRate = detailStockDTO.getDivRate();
        this.dps = detailStockDTO.getDps();
        this.date = convertStringToLocalDateTime(detailStockDTO.getDate());
    }

    public static DetailStockResponseDTO toDTO(DetailStock detailStock, String name) {
        return DetailStockResponseDTO.builder()
                .name(name)
                .pbr(detailStock.getPbr())
                .per(detailStock.getPer())
                .bps(detailStock.getBps())
                .divRate(detailStock.getDivRate())
                .dps(detailStock.getDps())
                .eps(detailStock.getEps())
                .date(detailStock.getDate())
                .ticker(detailStock.getTicker())
                .build();
    }

    private LocalDate convertStringToLocalDateTime(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(dateString, formatter);

        return localDate;
    }
}
