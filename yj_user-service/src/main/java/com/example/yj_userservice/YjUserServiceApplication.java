package com.example.yj_userservice;

import com.example.yj_userservice.exception.FeignErrorDecoder;
import feign.Logger;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@SpringBootApplication
public class YjUserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(YjUserServiceApplication.class, args);
    }
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;}

    @Bean
    public FeignErrorDecoder getFeignErrorDecoder() {
        return new FeignErrorDecoder();
    }
}
