package com.rlb.oc.service.impl;

import com.rlb.oc.OrderStatus;
import com.rlb.oc.event.OrderCreateEvent;
import com.rlb.oc.dto.OrderCreateDto;
import com.rlb.oc.exception.RecordNotFound;
import com.rlb.oc.kafka.producer.OrderPublisher;
import com.rlb.oc.service.OrderService;
import com.rlb.oc.mapper.OrderMapper;
import com.rlb.oc.model.Order;
import com.rlb.oc.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderPublisher orderPublisher;

    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    @Override
    public String placeOrder(OrderCreateDto dto) {
        Order savedOrder = orderRepository.save(OrderMapper.toEntity(dto));
        OrderCreateEvent event = OrderMapper.toEvent(savedOrder);
        orderPublisher.publishOrderPlaceEvent(event);
        return "Order Created Successfully";
    }

    @Override
    public ResponseEntity<String> getOrderStatus(String id) {
            Optional<Order> order = orderRepository.findById(id);
            if(order.isEmpty()){
                logger.error("OrderServiceImpl - getOrderStatus - Order not found for orderId : {}", id);
                throw new RecordNotFound("Order not found");
            }
            String status = order.get().getStatus().toString();
            return new ResponseEntity<>(status, HttpStatus.OK);
    }
}
