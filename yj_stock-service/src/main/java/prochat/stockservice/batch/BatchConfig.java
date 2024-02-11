package prochat.stockservice.batch;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import prochat.stockservice.model.StockEntity;

@Configuration
@EnableBatchProcessing
@RequiredArgsConstructor
public class BatchConfig {



}
