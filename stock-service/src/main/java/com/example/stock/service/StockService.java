package com.example.stock.service;

import com.example.stock.model.Stock;
import com.example.stock.repository.BatchRepository;
import com.example.stock.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class StockService {
    @Autowired
    private StockRepository stockRepository;

    public Page<Stock> findAll(Pageable pageable) {
        return stockRepository.findAll(pageable);
    }
}
