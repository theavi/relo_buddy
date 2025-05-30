package com.rlb.oh.service;

import com.rlb.oc.event.OrderCreateEvent;
import org.springframework.http.ResponseEntity;

public interface OrderHandleService {

    public ResponseEntity<String> handleOrder(OrderCreateEvent event);
}
