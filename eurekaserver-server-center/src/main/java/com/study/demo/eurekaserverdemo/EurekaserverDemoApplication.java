package com.study.demo.eurekaserverdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class EurekaserverDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(EurekaserverDemoApplication.class, args);
    }
}
