package com.rlb.oc.event.service.impl;

import com.rlb.oc.OrderStatus;
import com.rlb.oc.event.OrderCreateEvent;
import com.rlb.oc.event.kafka.producer.OrderPublisher;
import com.rlb.oc.event.service.OrderService;
import com.rlb.oc.mapper.OrderMapper;
import com.rlb.oc.model.Order;
import com.rlb.oc.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class OrderServiceImpl implements OrderService {
// make message final
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderPublisher orderPublisher;

    @Override
    public String placeOrder(OrderCreateEvent event) {
        Order order =  OrderMapper.toEntity(event);
        Order saveOrder = orderRepository.save(order);
        event.setStatus(OrderStatus.PLACED);
        event.setId(saveOrder.getId());
        orderPublisher.publishOrderPlaceEvent(event);
        return "Order Created Successfully";
    }
}
