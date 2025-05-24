package com.rlb.notification;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
public class RlbNotificationApplication {

    public static void main(String[] args) {
        SpringApplication.run(RlbNotificationApplication.class,args);
    }

    @PostConstruct
    public void init() {
        System.out.println("RLB Notification App Started");
    }
}
