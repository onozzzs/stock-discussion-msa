package com.example.stock.service;

import com.example.stock.dto.ClosePriceDTO;

import java.util.List;

@FunctionalInterface
public interface IndicatorCalculator {
    Double calculate(List<ClosePriceDTO> stocks);
}
