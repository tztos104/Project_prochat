package prochat.yj_batchstockservice.batch;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import prochat.yj_batchstockservice.service.CrawlingService;

@Component
@RequiredArgsConstructor
public class BatchScheduler {

    private  final CrawlingService crawlingService;
    private final JobLauncher jobLauncher;

    private final Job job;

    @Scheduled(cron = "0 25 18 ? * *") // 매일 새벽 1시에 실행 (초, 분, 시, 일, 월, 주 순서)
    public void runBatchJob() throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {
        crawlingService.StockData();
        JobParameters jobParameters = new JobParameters();
        jobLauncher.run(job, jobParameters);


    }
}
