package com.rlb.oc.service;

import com.rlb.oc.dto.OrderCreateDto;

public interface OrderService {

    String placeOrder(OrderCreateDto dto);

    String getOrderStatus(String id);

    String updateOrder(OrderCreateDto orderDto);

    String deleteOrder(String id);
}
