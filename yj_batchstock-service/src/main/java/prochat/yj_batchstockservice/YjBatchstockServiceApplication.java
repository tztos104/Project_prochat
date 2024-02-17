package prochat.yj_batchstockservice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@Slf4j
@SpringBootApplication
public class YjBatchstockServiceApplication {

    public static void main(String[] args) {
        int exit = SpringApplication.exit(SpringApplication.run(YjBatchstockServiceApplication.class, args));
        log.info("exit = {}", exit);
        System.exit(exit);
    }

}
