package com.rlb.oc.service;

import com.rlb.oc.OrderStatus;
import com.rlb.oc.dto.OrderCreateDto;
import org.springframework.http.ResponseEntity;

public interface OrderService {

    public abstract String placeOrder(OrderCreateDto dto);

    public ResponseEntity<String> getOrderStatus(String id);
}
