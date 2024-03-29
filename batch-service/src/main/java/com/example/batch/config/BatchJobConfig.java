package com.example.batch.config;

import com.example.batch.csv.CsvReader;
import com.example.batch.csv.DetailStockItemWriter;
import com.example.batch.csv.StockItemWriter;
import com.example.batch.dto.DetailStockDTO;
import com.example.batch.model.DetailStock;
import com.example.batch.model.Stock;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class BatchJobConfig {
    private final CsvReader csvReader;
    private final StockItemWriter stockItemWriter;
    private final DetailStockItemWriter detailStockItemWriter;

    private static final int chunkSize = 1000;

    @Bean
    public Job csvFileItemReaderJob(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new JobBuilder("csvFileItemReaderJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(stockItemReaderStep(jobRepository, transactionManager))
                .next(detailItemReaderStep(jobRepository, transactionManager))
                .build();
    }

    @JobScope
    @Bean
    public Step stockItemReaderStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        log.info("===========stockItemReaderStep");
        return new StepBuilder("stockItemReaderStep", jobRepository)
                .<Stock, Stock>chunk(chunkSize, transactionManager)
                .reader(csvReader.stockItemReader())
                .writer(stockItemWriter)
                .build();
    }

    @JobScope
    @Bean
    public Step detailItemReaderStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("detailItemReaderStep", jobRepository)
                .<DetailStockDTO, DetailStock>chunk(chunkSize, transactionManager)
                .reader(csvReader.detailItemReader())
                .processor(detailStockItemProcessor())
                .writer(detailStockItemWriter)
                .build();
    }

    @StepScope
    @Bean
    public ItemProcessor<DetailStockDTO, DetailStock> detailStockItemProcessor() {
        return new ItemProcessor<DetailStockDTO, DetailStock>() {
            @Override
            public DetailStock process(DetailStockDTO item) throws Exception {
                return new DetailStock(item);
            }
        };
    }
}
