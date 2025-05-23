package com.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class RlbCoreApplication {
    public static void main(String[] args) {
        System.out.println(args[0]);
        SpringApplication.run(RlbCoreApplication.class);
    }
}