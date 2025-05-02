package com.rlb.oc.event.service;

import com.rlb.oc.event.OrderCreateEvent;
import com.rlb.oc.event.dto.OrderCreateDto;

public interface OrderService {

    public abstract String placeOrder(OrderCreateDto dto);
}
