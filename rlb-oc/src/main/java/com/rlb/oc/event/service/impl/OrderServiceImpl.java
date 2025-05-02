package com.rlb.oc.event.service.impl;

import com.rlb.oc.OrderStatus;
import com.rlb.oc.event.OrderCreateEvent;
import com.rlb.oc.event.dto.OrderCreateDto;
import com.rlb.oc.event.kafka.producer.OrderPublisher;
import com.rlb.oc.event.service.OrderService;
import com.rlb.oc.mapper.OrderMapper;
import com.rlb.oc.model.Order;
import com.rlb.oc.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {
// make message final
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderPublisher orderPublisher;

    @Override
    public String placeOrder(OrderCreateDto dto) {
        Order savedOrder = orderRepository.save(OrderMapper.toEntity(dto));
        OrderCreateEvent event = OrderMapper.toEvent(savedOrder);
        orderPublisher.publishOrderPlaceEvent(event);
        return "Order Created Successfully";
    }
}
