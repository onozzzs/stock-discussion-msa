package com.example.stock.service;

import com.example.stock.dto.ChartDTO;
import com.example.stock.dto.DetailStockResponseDTO;
import com.example.stock.dto.SearchDTO;
import com.example.stock.model.DailyStock;
import com.example.stock.model.DailyStockToday;
import com.example.stock.model.DetailStock;
import com.example.stock.model.Stock;
import com.example.stock.repository.DailyStockRepository;
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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Slf4j
@Service
public class StockService {
    @Value("${spring.data.web.pageable.default-page-size}")
    private int PAGE_SIZE;

    @Autowired
    private DailyStockTodayRepository dailyStockTodayRepository;

    @Autowired
    private DailyStockRepository dailyStockRepository;

    @Autowired
    private DetailStockRepository detailStockRepository;

    @Autowired
    private StockRepository stockRepository;

    public Page<DailyStock> findAll(int page, String sortBy, String order) {
        Sort sort;
        if (order.equals("desc")) {
            sort = Sort.by(Sort.Order.desc(sortBy));
        } else {
            sort = Sort.by(Sort.Order.asc(sortBy));
        }

        Pageable pageable = PageRequest.of(page, PAGE_SIZE, sort);
        return dailyStockRepository.findLatestByName(pageable);
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

    public List<ChartDTO> retrieveChart(String term, String ticker) {
        LocalDate startDate;
        LocalDate endDate = LocalDate.now();

        log.info("term" + term + " ticker" + ticker);
        if (term.equals("1month")) {
            startDate = endDate.minusMonths(1);
            log.info("startDate + " + startDate);
        } else if (term.equals("1year")) {
            startDate = endDate.minusYears(1);
        } else if (term.equals("3year")) {
            startDate = endDate.minusYears(3);
        } else if (term.equals("5year")) {
            startDate = endDate.minusYears(5);
        } else {
            throw new IllegalArgumentException("Invalid term value");
        }

        List<DailyStock> stocks = dailyStockRepository.findByTickerAndDateBetween(ticker, startDate, endDate);
        return convertToChartDTO(stocks);
    }

    private List<ChartDTO> convertToChartDTO(List<DailyStock> stocks) {
        List<ChartDTO> chartDTOs = stocks.stream().map(ChartDTO::new).collect(Collectors.toList());
        return chartDTOs;
    }
}
