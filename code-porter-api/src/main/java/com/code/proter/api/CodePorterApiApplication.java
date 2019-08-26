package com.code.proter.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class CodePorterApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(CodePorterApiApplication.class, args);
    }

}
