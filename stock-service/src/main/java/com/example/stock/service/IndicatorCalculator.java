package com.example.stock.service;

import com.example.stock.model.DailyStock;

import java.util.List;

@FunctionalInterface
public interface IndicatorCalculator {
    Double calculate(List<DailyStock> stocks);
}
