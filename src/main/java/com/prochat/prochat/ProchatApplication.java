package com.prochat.prochat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class ProchatApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProchatApplication.class, args);
    }

}
