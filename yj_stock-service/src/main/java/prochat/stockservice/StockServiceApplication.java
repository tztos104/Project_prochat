package prochat.stockservice;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@EnableBatchProcessing
@SpringBootApplication
public class StockServiceApplication {


    public static void main(String[] args) {
        SpringApplication.run(StockServiceApplication.class, args);
    }

}
