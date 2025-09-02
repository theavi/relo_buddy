package com.rlb.oc.service;

import com.rlb.oc.dto.OrderCreateDto;
import org.springframework.http.ResponseEntity;

public interface OrderService {

    public abstract String placeOrder(OrderCreateDto dto);

    public String getOrderStatus(String id);

    ResponseEntity<String> updateOrder(OrderCreateDto orderDto);
}
