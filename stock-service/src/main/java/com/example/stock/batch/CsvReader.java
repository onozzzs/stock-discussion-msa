package com.example.stock.batch;

import com.example.stock.dto.DetailStockDTO;
import com.example.stock.dto.StockDTO;
import com.example.stock.model.Stock;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.validation.BindException;

@Configuration
public class CsvReader {

    @Bean
    @StepScope
    public FlatFileItemReader<Stock> stockItemReader() {
        return new FlatFileItemReaderBuilder<Stock>()
                .name("stockItemReader")
                .resource(new FileSystemResource("C:/Users/wwwoo/Desktop/multi-module/csv/stock_all.csv"))
                .lineTokenizer(new DelimitedLineTokenizer())
                .linesToSkip(1)
                .encoding("UTF-8")
                .fieldSetMapper(new FieldSetMapper<Stock>() {
                    @Override
                    public Stock mapFieldSet(FieldSet fieldSet) throws BindException {
                        return Stock.builder()
                                .stockName(fieldSet.readString(1))
                                .ticker(fieldSet.readString(2))
                                .build();
                    }
                })
                .build();
    }

    @Bean
    @StepScope
    public FlatFileItemReader<StockDTO> csvFileItemReader() {
        return new FlatFileItemReaderBuilder<StockDTO>()
                .name("csvFileItemReaderStep")
                .resource(new FileSystemResource("C:/Users/wwwoo/Desktop/multi-module/csv/stock-daily-5.csv"))
                .lineTokenizer(new DelimitedLineTokenizer())
                .linesToSkip(1)
                .encoding("UTF-8")
                .fieldSetMapper(new StockFieldSetMapper())
                .build();
    }

    @Bean
    @StepScope
    public FlatFileItemReader<DetailStockDTO> detailItemReader() {
        return new FlatFileItemReaderBuilder<DetailStockDTO>()
                .name("detailItemReaderStep")
                .resource(new FileSystemResource("C:/Users/wwwoo/Desktop/multi-module/csv/total_fundamental.csv"))
                .lineTokenizer(new DelimitedLineTokenizer())
                .linesToSkip(1)
                .encoding("UTF-8")
                .fieldSetMapper(new DetailStockFieldSetMapper())
                .build();
    }
}

