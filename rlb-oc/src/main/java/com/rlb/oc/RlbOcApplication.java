package com.rlb.oc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;


@SpringBootApplication
@EnableCaching
public class RlbOcApplication {
    public static void main(String[] args) {
        SpringApplication.run(RlbOcApplication.class, args);
    }
}