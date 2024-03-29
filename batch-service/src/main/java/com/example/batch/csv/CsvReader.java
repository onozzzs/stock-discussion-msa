package com.example.batch.csv;

import com.example.batch.config.DetailStockFieldSetMapper;
import com.example.batch.dto.DetailStockDTO;
import com.example.batch.model.Stock;
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
//                .resource(new FileSystemResource("/var/jenkins_home/workspace/spring-batch/csv/stock_all.csv"))
                .resource(new FileSystemResource("C:/Users/wwwoo/Desktop/multi-module/batch-service/csv/stock_all.csv"))
                .lineTokenizer(new DelimitedLineTokenizer())
                .linesToSkip(1)
                .encoding("UTF-8")
                .fieldSetMapper(new FieldSetMapper<Stock>() {
                    @Override
                    public Stock mapFieldSet(FieldSet fieldSet) throws BindException {
                        return Stock.builder()
                                .ticker(fieldSet.readString(1))
                                .stockName(fieldSet.readString(2))
                                .market(fieldSet.readString(3))
                                .build();
                    }
                })
                .build();
    }

    @Bean
    @StepScope
    public FlatFileItemReader<DetailStockDTO> detailItemReader() {
        return new FlatFileItemReaderBuilder<DetailStockDTO>()
                .name("detailItemReaderStep")
//                .resource(new FileSystemResource("/var/jenkins_home/workspace/spring-batch/csv/total_fundamental.csv"))
                .resource(new FileSystemResource("C:/Users/wwwoo/Desktop/multi-module/batch-service/csv//total_fundamental.csv"))
                .lineTokenizer(new DelimitedLineTokenizer())
                .linesToSkip(1)
                .encoding("UTF-8")
                .fieldSetMapper(new DetailStockFieldSetMapper())
                .build();
    }
}

