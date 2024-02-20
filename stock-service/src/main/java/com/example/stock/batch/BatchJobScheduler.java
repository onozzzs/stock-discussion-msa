package com.example.stock.batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class BatchJobScheduler {
    private final JobLauncher jobLauncher;
    private final Job csvFileItemReaderJob;

    public BatchJobScheduler(JobLauncher jobLauncher, Job csvFileItemReaderJob) {
        this.jobLauncher = jobLauncher;
        this.csvFileItemReaderJob = csvFileItemReaderJob;
    }

    @Scheduled(cron = "0 0 17 * * MON-FRI")
    public void runCsvFileItemReaderJob() throws Exception {
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("time", System.currentTimeMillis())
                .toJobParameters();

        jobLauncher.run(csvFileItemReaderJob, jobParameters);
    }
}
