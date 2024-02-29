package com.example.stock.service;

import com.example.stock.dto.MovingAverageDTO;
import com.example.stock.model.StockIndicator;
import com.example.stock.repository.StockIndicatorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class StockIndicatorService {
    @Autowired
    private StockIndicatorRepository stockIndicatorRepository;

    public Page<MovingAverageDTO> getMovingAverages(Pageable pageable, String ticker) {
        Page<StockIndicator> stockIndicatorPage = stockIndicatorRepository.findByTicker(pageable, ticker);
        Page<MovingAverageDTO> movingAveragePage = stockIndicatorPage.map(MovingAverageDTO::new);
        return movingAveragePage;
    }
}
