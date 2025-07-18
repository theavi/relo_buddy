package com.rlb.oh;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;


@SpringBootApplication
@EnableFeignClients
public class RLBOHApplication {
    public static void main(String[] args) {
        SpringApplication.run(RLBOHApplication.class, args);
    }
}