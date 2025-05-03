package com.rlb.oc.service;

import com.rlb.oc.OrderCreateEvent;

public interface OrderService {

    public abstract String placeOrder(OrderCreateEvent event);
}
