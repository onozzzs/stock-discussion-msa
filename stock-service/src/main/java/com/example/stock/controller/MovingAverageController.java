package com.example.stock.controller;

import com.example.stock.service.MovingAverageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/stock/")
public class MovingAverageController {
    @Autowired
    MovingAverageService movingAverageService;

    @GetMapping("/moving-average")
    public ResponseEntity<?> getMovingAverage(@RequestParam String ticker, @RequestParam String date, @RequestParam int days) {
        double movingAverage = movingAverageService.calculateIndicator(ticker, date, days, "movingAverage");
        return ResponseEntity.ok().body(movingAverage);
    }
}
