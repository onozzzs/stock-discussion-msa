package com.example.stock.batch;

import com.example.stock.dto.StockDTO;
import com.example.stock.model.Stock;
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
import org.springframework.batch.item.support.CompositeItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.Arrays;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class FilterItemReaderJobConfig {
    private final CsvReader csvReader;
    private final CsvWriter csvWriter;
    private final DailyStockWriter dailyStockWriter;

    private static final int chunkSize = 1000;

    @Bean
    public Job csvFileItemReaderJob(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new JobBuilder("csvFileItemReaderJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(csvFileItemReaderStep(jobRepository, transactionManager))
                .build();
    }

    @JobScope
    @Bean
    public Step csvFileItemReaderStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("csvFileItemReaderStep", jobRepository)
                .<StockDTO, Stock>chunk(chunkSize, transactionManager)
                .reader(csvReader.csvFileItemReader())
                .processor(stockItemProcessor())
                .writer(compositeItemWriter())
                .build();
    }

    @StepScope
    @Bean
    public ItemProcessor<StockDTO, Stock> stockItemProcessor() {
        return new ItemProcessor<StockDTO, Stock>() {
            @Override
            public Stock process(StockDTO item) throws Exception {
                return new Stock(item);
            }
        };
    }

    @StepScope
    public CompositeItemWriter<Stock> compositeItemWriter() {
        final CompositeItemWriter<Stock> compositeItemWriter = new CompositeItemWriter<>();
        compositeItemWriter.setDelegates(Arrays.asList(csvWriter, dailyStockWriter));
        return compositeItemWriter;
    }
}
