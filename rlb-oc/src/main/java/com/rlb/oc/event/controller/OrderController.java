package com.rlb.oc.event.controller;

import com.rlb.oc.event.OrderCreateEvent;
import com.rlb.oc.event.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/oc")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/order")
    public ResponseEntity<String> placeOrder(@RequestBody OrderCreateEvent event) {
        String result = orderService.placeOrder(event);
        return new ResponseEntity<String>(result, HttpStatus.CREATED);
    }
}
