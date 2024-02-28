package com.example.batch.stockIndicator;

import com.example.batch.dailyStock.XmlParser;
import com.example.batch.model.DailyStock;
import com.example.batch.model.Stock;
import com.example.batch.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class StockIndicatorReader implements ItemReader<List<DailyStock>> {
    private static String apiUrl = "https://fchart.stock.naver.com/sise.nhn?";

    private Iterator<Stock> iterator;
    private List<Stock> stocks;
    private List<DailyStock> dailyStocks;
    private final StockRepository stockRepository;

    @Autowired
    private XmlParser xmlParser;

    private static int COUNT = 0;

    @BeforeStep
    public void beforeStep() {
        log.info("beforestep---------------------------------");
        stocks = stockRepository.findAll();
        this.iterator = stocks.iterator();
    }

    @Override
    public List<DailyStock> read() throws Exception {
        if(!iterator.hasNext()) return null;
        COUNT++;
        dailyStocks = new ArrayList<>();
        String symbol = iterator.next().getTicker();
        String url = apiUrl + "symbol=" + String.valueOf(symbol) + "&timeframe=day&count=1200&requestType=0";
        String xmlResponse = fetchDataFromApi(url);
        dailyStocks = xmlParser.parseXml(xmlResponse, symbol);
        return dailyStocks;
    }

    private String fetchDataFromApi(String apiUrl) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(apiUrl, String.class);
    }
}
