package com.rlb.consumer;

import com.rlb.payload.UserCreatedEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class UserHandler {

    @KafkaListener(topics = "user.created.v1", groupId = "")
    public void handleUserCreated(UserCreatedEvent event){
        method1();
    }

    private void method1() {
    }
}
