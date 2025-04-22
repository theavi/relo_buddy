package com.rlb.producer;

import com.rlb.payload.UserCreatedEvent;
import org.springframework.stereotype.Service;

@Service
public class UserPublisher {
    public void publishUserCreatedEvent(UserCreatedEvent event){

    }
}
