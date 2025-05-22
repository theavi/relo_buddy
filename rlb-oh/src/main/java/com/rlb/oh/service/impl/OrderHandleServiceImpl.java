package com.rlb.oh.service.impl;

import com.rlb.oc.event.OrderCreateEvent;
import com.rlb.oh.consumer.OrderCreatedEventListener;
import com.rlb.oh.service.OrderHandleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;

public class OrderHandleServiceImpl implements OrderHandleService {

    private static final Logger logger = LoggerFactory.getLogger(OrderHandleServiceImpl.class);

    @Override
    public ResponseEntity<String> handleOrder(OrderCreateEvent event) {

        return null;
    }
}
