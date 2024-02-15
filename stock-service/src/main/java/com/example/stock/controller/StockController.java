package com.example.stock.controller;

import com.example.stock.model.Stock;
import com.example.stock.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/stock")
public class StockController {
    @Autowired
    private StockService stockService;

    @GetMapping
    public ResponseEntity<?> page(Pageable pageable) {
        Page<Stock> stockPage = stockService.findAll(pageable);
        return ResponseEntity.ok().body(stockPage);
    }
}
