package com.example.stock.schedule;

import com.example.stock.batch.FilterItemReaderJobConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;

@Slf4j
@Component
public class BatchScheduler {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private FilterItemReaderJobConfig jobConfig;

    private JobRepository jobRepository;
    private PlatformTransactionManager transactionManager;

    @Scheduled(cron = "0 0 18 * * MON-FRI")
    public void runBatchJob() {
        try {
            // Job 실행을 위한 파라미터 설정 (시간을 사용하여 고유한 Job Parameter 생성)
            JobParameters jobParameters = new JobParametersBuilder()
                    .addLong("timestamp", System.currentTimeMillis())
                    .toJobParameters();

            // Job 실행
            JobExecution jobExecution = jobLauncher.run(jobConfig.csvFileItemReaderJob(jobRepository, transactionManager), jobParameters);

            // Job 실행 결과 등을 확인할 수 있음 (jobExecution.getStatus() 등)
            System.out.println("Job Execution Status: " + jobExecution.getStatus());
        } catch (Exception e) {
            // 예외 처리
            e.printStackTrace();
        }
    }
}
