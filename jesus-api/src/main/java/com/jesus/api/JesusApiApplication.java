package com.jesus.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class JesusApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(JesusApiApplication.class, args);
    }

}
