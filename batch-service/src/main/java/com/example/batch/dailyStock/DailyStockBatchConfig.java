package com.example.batch.dailyStock;

import com.example.batch.model.DailyStock;
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
public class DailyStockBatchConfig {
    private final DailyStockItemReader dailyStockItemReader;
    private final DailyStockItemWriter dailyStockItemWriter;

    @Bean
    public Job dailyStockJob(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new JobBuilder("dailyStockJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(dailyStockStep(jobRepository, transactionManager))
                .build();
    }

    @JobScope
    @Bean
    public Step dailyStockStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("dailyStockStep", jobRepository)
                .<List<DailyStock>, List<DailyStock>>chunk(1000, transactionManager)
                .reader(dailyStockItemReader)
                .writer(dailyStockItemWriter)
                .build();
    }
}
