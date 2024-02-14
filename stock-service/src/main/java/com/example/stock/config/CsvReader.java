package com.example.stock.config;

import com.example.stock.dto.StockDTO;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

@Configuration
public class CsvReader {

    @Bean
    @StepScope
    public FlatFileItemReader<StockDTO> csvFileItemReader() {
        return new FlatFileItemReaderBuilder<StockDTO>()
                .name("csvFileItemReader")
                .resource(new FileSystemResource("C:/Users/wwwoo/Desktop/multi-module/csv/stock-daily-5.csv"))
                .lineTokenizer(new DelimitedLineTokenizer())
                .linesToSkip(1)
                .encoding("UTF-8")
                .fieldSetMapper(new StockFieldSetMapper())
                .build();
    }
}

