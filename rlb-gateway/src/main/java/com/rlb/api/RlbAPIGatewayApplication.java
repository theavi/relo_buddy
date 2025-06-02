package com.rlb.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RlbAPIGatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(RlbAPIGatewayApplication.class);

        System.out.println("RLB API Gateway Started.");
    }
}