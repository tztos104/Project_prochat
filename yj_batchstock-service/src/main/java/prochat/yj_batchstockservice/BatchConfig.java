package prochat.yj_batchstockservice;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.*;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;

import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;

import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;

import org.springframework.batch.item.ItemProcessor;

import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.data.builder.RepositoryItemReaderBuilder;

import org.springframework.batch.item.data.builder.RepositoryItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.core.task.TaskExecutor;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;
import prochat.yj_batchstockservice.model.StockDataEntity;
import prochat.yj_batchstockservice.model.StockEntity;
import prochat.yj_batchstockservice.repository.StockDataEntityRepository;
import prochat.yj_batchstockservice.repository.StockRepository;
import prochat.yj_batchstockservice.service.CrawlingService;

import java.util.Collections;
import java.util.List;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class BatchConfig {

    private final StockRepository stockRepository;
    private final StockDataEntityRepository stockDataEntityRepository;
    private final CrawlingService crawlingService;

    @Bean
    public Job simpleJob1(JobRepository jobRepository, Step dateStep, Step step2) {
        return new JobBuilder("simpleJob545423q", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(dateStep)

                .build();
    }

    @Bean
    public Step dateStep(JobRepository jobRepository, Tasklet testTasklet, PlatformTransactionManager platformTransactionManager) {
        log.info("여기와1");

        return new StepBuilder("dateStep", jobRepository)

                .<StockEntity,  List<StockDataEntity>>chunk(500, platformTransactionManager)
                .reader(dataReader())
                .processor(dataProcessor())
                .writer(dataWriter())
                .taskExecutor(taskExecutor())
                .build();
    }
    @Bean
    public ItemProcessor<StockEntity, List<StockDataEntity>> dataProcessor() {
        return stockEntity -> crawlingService.crawlAndSaveStockData(stockEntity.getStockCode(), "day", "240");
    }


    @Bean
    public RepositoryItemReader<StockEntity> dataReader() {
        log.info("여기와4");
        return new RepositoryItemReaderBuilder<StockEntity>()
                .name("stock")
                .repository(stockRepository)
                .methodName("findAll")
                .pageSize(5)
                .arguments(List.of())
                .sorts(Collections.singletonMap("id", Sort.Direction.ASC))
                .build();
    }

    @Bean
    @StepScope
    public Tasklet testTasklet() {
        return new TestTasklet() {

        };
    }

    @Bean
    public ItemWriter< List<StockDataEntity>> dataWriter() {
        return stockDataEntityList -> {
            // 여기서 필요에 따라 데이터를 저장하는 작업을 수행합니다.
            for (List<StockDataEntity> stockDataEntity : stockDataEntityList) {
                stockDataEntityRepository.saveAll(stockDataEntity);
            }
        };
    }

    @Bean
    public TaskExecutor taskExecutor(){
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(4);
        executor.setMaxPoolSize(8);
        executor.setThreadNamePrefix("async-thread-");
        return executor;
    }
}
