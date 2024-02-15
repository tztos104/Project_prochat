package prochat.yj_activityservice;


import feign.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;



@EnableDiscoveryClient
@SpringBootApplication
public class YjActivityServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(YjActivityServiceApplication.class, args);
    }

    @Bean
    public Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;}


}
