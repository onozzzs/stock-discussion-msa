package com.example.stock.controller;

import com.example.stock.dto.SearchDTO;
import com.example.stock.model.DailyStock;
import com.example.stock.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/stock")
public class StockController {
    @Autowired
    private StockService stockService;

    @GetMapping
    public ResponseEntity<?> page(Pageable pageable) {
        Page<DailyStock> stockPage = stockService.findAll(pageable);
        return ResponseEntity.ok().body(stockPage);
    }

    @GetMapping("/search")
    public ResponseEntity<?> search(@RequestBody SearchDTO searchDTO) {
        Page<DailyStock> stockPage = stockService.search(searchDTO);
        return ResponseEntity.ok().body(stockPage);
    }
}
