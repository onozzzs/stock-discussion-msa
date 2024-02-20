package com.example.batch.component.reader;

import com.example.batch.config.XmlParser;
import com.example.batch.model.DailyStock;
import com.example.batch.model.Stock;
import com.example.batch.repository.StockRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class DailyStockItemReader implements ItemReader<List<DailyStock>> {

    private String apiUrl = "https://fchart.stock.naver.com/sise.nhn?";

    private List<Stock> stocks;
    private List<DailyStock> dailyStocks;

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private XmlParser xmlParser;

    @Override
    public List<DailyStock> read() throws Exception {
        if (dailyStocks == null) {
            dailyStocks = new ArrayList<>();
            stocks = stockRepository.findAll();
            log.info("==========================reader, read");
            int i = 0;
            for (Stock stock : stocks) {
                i += 1;
                String symbol = stock.getTicker();
                String url = apiUrl + "symbol=" + String.valueOf(symbol) + "&timeframe=day&count=1266&requestType=0";
                String xmlResponse = fetchDataFromApi(url);
                dailyStocks.addAll(xmlParser.parseXml(xmlResponse, symbol));
                if (i > 2) {
                    break;
                }
            }
            return dailyStocks;
        }
        return null;
    }

    private String fetchDataFromApi(String apiUrl) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(apiUrl, String.class);
    }
}
