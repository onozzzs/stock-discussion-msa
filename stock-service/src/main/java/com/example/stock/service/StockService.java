package com.example.stock.service;

import com.example.stock.dto.SearchDTO;
import com.example.stock.model.DailyStock;
import com.example.stock.repository.DailyStockRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class StockService {
    @Value("${spring.data.web.pageable.default-page-size}")
    private int PAGE_SIZE;

    @Autowired
    private DailyStockRepository dailyStockRepository;

    public Page<DailyStock> findAll(int page, String sortBy, String order) {
        Sort sort;
        if (order.equals("desc")) {
            sort = Sort.by(Sort.Order.desc(sortBy));
        } else {
            sort = Sort.by(Sort.Order.asc(sortBy));
        }

        Pageable pageable = PageRequest.of(page, PAGE_SIZE, sort);
        return dailyStockRepository.findAll(pageable);
    }

    public Page<DailyStock> search(SearchDTO searchDTO) {
        Pageable pageable = PageRequest.of(searchDTO.getPage(), PAGE_SIZE);
        if (searchDTO.getType().equals("name")) {
            return dailyStockRepository.findByStockNameContaining(pageable, searchDTO.getKeyword());
        }
        if (searchDTO.getType().equals("market")) {
            return dailyStockRepository.findByMarketContaining(pageable, searchDTO.getKeyword());
        }
        return dailyStockRepository.findByTickerContaining(pageable, searchDTO.getKeyword());
    }
}
