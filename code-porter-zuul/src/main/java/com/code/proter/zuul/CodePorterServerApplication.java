package com.code.proter.zuul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication
@EnableEurekaClient
@EnableZuulProxy //网关
public class CodePorterServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(CodePorterServerApplication.class, args);
    }

}
