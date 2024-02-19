package com.example.stock.batch;

import com.example.stock.dto.DetailStockDTO;
import com.example.stock.dto.StockDTO;
import com.example.stock.model.DailyStock;
import com.example.stock.model.DetailStock;
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
    private final StockItemWriter stockItemWriter;
    private final DailyStockWriter dailyStockWriter;
    private final DetailStockItemWriter detailStockItemWriter;

    private static final int chunkSize = 1000;

    @Bean
    public Job csvFileItemReaderJob(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new JobBuilder("csvFileItemReaderJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(csvFileItemReaderStep(jobRepository, transactionManager))
                .next(detailItemReaderStep(jobRepository, transactionManager))
                .next(stockItemReaderStep(jobRepository, transactionManager))
                .build();
    }

    @JobScope
    @Bean
    public Step stockItemReaderStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("stockItemReaderStep", jobRepository)
                .<Stock, Stock>chunk(chunkSize, transactionManager)
                .reader(csvReader.stockItemReader())
                .writer(stockItemWriter)
                .build();
    }

    @JobScope
    @Bean
    public Step csvFileItemReaderStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("csvFileItemReaderStep", jobRepository)
                .<StockDTO, DailyStock>chunk(chunkSize, transactionManager)
                .reader(csvReader.csvFileItemReader())
                .processor(stockItemProcessor())
                .writer(compositeItemWriter())
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
    public ItemProcessor<StockDTO, DailyStock> stockItemProcessor() {
        return new ItemProcessor<StockDTO, DailyStock>() {
            @Override
            public DailyStock process(StockDTO item) throws Exception {
                return new DailyStock(item);
            }
        };
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

    @StepScope
    public CompositeItemWriter<DailyStock> compositeItemWriter() {
        final CompositeItemWriter<DailyStock> compositeItemWriter = new CompositeItemWriter<>();
        compositeItemWriter.setDelegates(Arrays.asList(csvWriter, dailyStockWriter));
        return compositeItemWriter;
    }
}
