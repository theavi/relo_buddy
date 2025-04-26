package com.rlb.oc.event.service.impl;

import com.rlb.oc.OrderStatus;
import com.rlb.oc.event.OrderCreateEvent;
import com.rlb.oc.event.kafka.producer.OrderPublisher;
import com.rlb.oc.event.service.OrderService;
import com.rlb.oc.model.Order;
import com.rlb.oc.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderPublisher orderPublisher;

    @Override
    public String placeOrder(OrderCreateEvent event) {

        Order order = orderRepository.save(new Order(23, new Date()));
        event.setId(order.getId());
        event.setStatus(OrderStatus.PLACED);
        orderPublisher.publishOrderPlaceEvent(event);
        return "Order Created Successfully";
    }
}
