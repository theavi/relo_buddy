package com.rlb.oh;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableKafka
public class RlbOhApplication {
    public static void main(String[] args) {
        SpringApplication.run(RlbOhApplication.class, args);
    }
}