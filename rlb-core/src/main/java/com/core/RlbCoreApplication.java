package com.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RlbCoreApplication {
    public static void main(String[] args) {
        System.out.println(args[0]);
        SpringApplication.run(RlbCoreApplication.class);
    }
}