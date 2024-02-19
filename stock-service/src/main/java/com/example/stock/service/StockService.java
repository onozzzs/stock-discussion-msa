package com.example.stock.service;

import com.example.stock.dto.DetailStockResponseDTO;
import com.example.stock.dto.SearchDTO;
import com.example.stock.model.DailyStockToday;
import com.example.stock.model.DetailStock;
import com.example.stock.model.Stock;
import com.example.stock.repository.DailyStockTodayRepository;
import com.example.stock.repository.DetailStockRepository;
import com.example.stock.repository.StockRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@Service
public class StockService {
    @Value("${spring.data.web.pageable.default-page-size}")
    private int PAGE_SIZE;

    @Autowired
    private DailyStockTodayRepository dailyStockTodayRepository;

    @Autowired
    private DetailStockRepository detailStockRepository;

    @Autowired
    private StockRepository stockRepository;

    public Page<DailyStockToday> findAll(int page, String sortBy, String order) {
        Sort sort;
        if (order.equals("desc")) {
            sort = Sort.by(Sort.Order.desc(sortBy));
        } else {
            sort = Sort.by(Sort.Order.asc(sortBy));
        }

        Pageable pageable = PageRequest.of(page, PAGE_SIZE, sort);
        return dailyStockTodayRepository.findAll(pageable);
    }

    public Page<DailyStockToday> search(SearchDTO searchDTO) {
        Pageable pageable = PageRequest.of(searchDTO.getPage(), PAGE_SIZE);
        if (searchDTO.getType().equals("name")) {
            return dailyStockTodayRepository.findByStockNameContaining(pageable, searchDTO.getKeyword());
        }
        if (searchDTO.getType().equals("market")) {
            return dailyStockTodayRepository.findByMarketContaining(pageable, searchDTO.getKeyword());
        }
        return dailyStockTodayRepository.findByTickerContaining(pageable, searchDTO.getKeyword());
    }

    public List<DetailStockResponseDTO> retrieve(String ticker) {
        Stock stock = stockRepository.findByTicker(ticker).orElseThrow(() -> new NoSuchElementException());
        String name = stock.getStockName();

        List<DetailStock> detailStocks = detailStockRepository.findTop4ByTickerOrderByDateDesc(ticker);
        List<DetailStockResponseDTO> responseDTOs = new ArrayList<>();
        for (DetailStock detailStock : detailStocks) {
            DetailStockResponseDTO responseDTO = DetailStock.toDTO(detailStock, name);
            responseDTOs.add(responseDTO);
        }

        return responseDTOs;
    }
}
