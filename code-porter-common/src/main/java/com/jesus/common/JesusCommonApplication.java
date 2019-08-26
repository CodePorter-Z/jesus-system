package com.jesus.common;

import com.jesus.common.base.expant.service.BaseDaoFactoryBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
//@EnableEurekaClient
// feign启动注解，可配置扫描指定包，默认是spring的扫描路径
//@EnableFeignClients
// 启动baseDao  jpa实现面向接口编程
@EnableJpaRepositories(repositoryFactoryBeanClass = BaseDaoFactoryBean.class, basePackages = "com.jesus.common.base.expant")
@EnableTransactionManagement
public class JesusCommonApplication {

    public static void main(String[] args) {
        SpringApplication.run(JesusCommonApplication.class, args);
    }

}
