package com.rlb.oh.consumer;

import com.rlb.oc.event.OrderCreateEvent;
import com.rlb.oh.service.OrderHandleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class OrderCreatedEventListener {

    @Autowired
    public OrderHandleService orderHandleService;

    private static final Logger logger = LoggerFactory.getLogger(OrderCreatedEventListener.class);

    @KafkaListener(topics = "order.created.v1", groupId = "rlbGroup")
    public void consume(OrderCreateEvent event){
        logger.info("OrderCreatedEventListener - consume: Event received : {}", event);
        orderHandleService.handleOrder(event);
    }


}
