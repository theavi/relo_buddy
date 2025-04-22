package com.rlb.oc.event.service.impl;

import com.rlb.oc.event.OrderCreateEvent;
import com.rlb.oc.event.kafka.producer.OrderPublisher;
import com.rlb.oc.event.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderPublisher orderPublisher;

    @Override
    public String placeOrder(OrderCreateEvent event) {
        orderPublisher.publishOrderPlaceEvent(event);
        return "Order Created Successfully";
    }
}
