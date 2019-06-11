package com.study.demo.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableJpaRepositories
@EnableTransactionManagement
@EnableEurekaClient
@SpringBootApplication
public class EurekaserverOrderServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(EurekaserverOrderServiceApplication.class, args);
    }

}
