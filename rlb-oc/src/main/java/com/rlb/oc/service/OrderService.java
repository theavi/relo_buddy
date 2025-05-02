package com.rlb.oc.service;

import com.rlb.oc.dto.OrderCreateDto;

public interface OrderService {

    public abstract String placeOrder(OrderCreateDto dto);
}
