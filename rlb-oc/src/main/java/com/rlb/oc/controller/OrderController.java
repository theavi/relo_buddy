package com.rlb.oc.controller;

import com.rlb.oc.dto.OrderCreateDto;
import com.rlb.oc.model.Order;
import com.rlb.oc.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/order-capture/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<String> placeOrder(@RequestBody OrderCreateDto orderCreateDto) {
        String orderId = orderService.placeOrder(orderCreateDto);
        return new ResponseEntity<>(orderId, HttpStatus.CREATED);
    }

    @GetMapping("/{id}/status")
    public ResponseEntity<String> getOrderStatus(@PathVariable String id){
        return new ResponseEntity<>(orderService.getOrderStatus(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateOrder(@PathVariable String id, @RequestBody OrderCreateDto order){
        return new ResponseEntity<>(orderService.updateOrder(order), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOrder(@PathVariable String id){
        return new ResponseEntity<>(orderService.deleteOrder(id), HttpStatus.NO_CONTENT);
    }
}
