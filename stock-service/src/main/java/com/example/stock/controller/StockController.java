package com.example.stock.controller;

import com.example.stock.dto.ChartDTO;
import com.example.stock.dto.DetailStockResponseDTO;
import com.example.stock.dto.SearchDTO;
import com.example.stock.dto.StockDTO;
import com.example.stock.model.DailyStock;
import com.example.stock.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/stock")
public class StockController {
    @Autowired
    private StockService stockService;

    @GetMapping("/test")
    public String test() {
        return "test";
    }

    @GetMapping("/{page}")
    public ResponseEntity<?> page(@PathVariable int page,
                                  @RequestParam(defaultValue = "id") String sortBy,
                                  @RequestParam(defaultValue = "desc") String order) {
        Page<DailyStock> stockPage = stockService.findAll(page, sortBy, order);
        return ResponseEntity.ok().body(stockPage);
    }

    @GetMapping("/search")
    public ResponseEntity<?> search(@RequestBody SearchDTO searchDTO) {
        Page<StockDTO> stockPage = stockService.search(searchDTO);
        return ResponseEntity.ok().body(stockPage);
    }

    @GetMapping("/detail")
    public ResponseEntity<?> retrieve(@RequestParam String ticker) {
        List<DetailStockResponseDTO> response = stockService.retrieve(ticker);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/chart")
    public ResponseEntity<?> retrieveChart(@RequestParam String term, @RequestParam String ticker) {
        List<ChartDTO> chartDTOs = stockService.retrieveChart(term, ticker);
        return ResponseEntity.ok().body(chartDTOs);
    }
}
