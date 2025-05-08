package com.rlb.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class OrderCreatedEventListener {

    @KafkaListener(topics = "order.created.v1", groupId = "order")
    public void consume(String message){
        System.out.println(message);
    }
}
