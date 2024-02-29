package com.example.stock.controller;

import com.example.stock.dto.MovingAverageDTO;
import com.example.stock.service.StockIndicatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/stock/")
public class StockIndicatorController {
    @Autowired
    StockIndicatorService stockIndicatorService;

    @GetMapping("/moving-average")
    public ResponseEntity<?> getMovingAverage(@RequestParam String ticker, Pageable pageable) {
        Page<MovingAverageDTO> movingAveragePage = stockIndicatorService.getMovingAverages(pageable, ticker);
        return ResponseEntity.ok().body(movingAveragePage);
    }
}
