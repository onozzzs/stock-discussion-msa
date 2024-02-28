package com.example.batch.stockIndicator;


import com.example.batch.model.DailyStock;
import com.example.batch.model.StockIndicator;
import com.example.batch.model.Stock;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.List;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class StockIndicatorBatchConfig {
    private final StockIndicatorReader stockIndicatorReader;
    private final StockIndicatorProcessor stockIndicatorProcessor;
    private final StockIndicatorWriter stockIndicatorWriter;

    @Bean
    public Job stockIndicatorJob(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new JobBuilder("stockIndicatorJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(stockIndicatorStep(jobRepository, transactionManager))
                .build();

    }

    @JobScope
    @Bean
    public Step stockIndicatorStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("stockIndicatorStep", jobRepository)
                .<List<DailyStock>, List<StockIndicator>>chunk(100, transactionManager)
                .reader(stockIndicatorReader)
                .processor(stockIndicatorProcessor)
                .writer(stockIndicatorWriter)
                .build();
    }
}
