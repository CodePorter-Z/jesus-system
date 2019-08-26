package com.code.proter.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class CodePorterCoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(CodePorterCoreApplication.class, args);
    }

}
