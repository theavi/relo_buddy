package com.rlb.oc.event.service;

import com.rlb.oc.event.OrderCreateEvent;

public interface OrderService {

    public abstract String placeOrder(OrderCreateEvent event);
}
