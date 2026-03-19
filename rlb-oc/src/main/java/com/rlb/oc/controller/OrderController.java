package com.rlb.oc.controller;

import com.rlb.oc.dto.OrderCreateDto;
import com.rlb.oc.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/oc")
public class OrderController {

    @Autowired
    private OrderService orderService;


    @PostMapping("/createOrder")
    public ResponseEntity<String> placeOrder(@RequestBody OrderCreateDto orderCreateDto) {
        String orderId = orderService.placeOrder(orderCreateDto);
        return new ResponseEntity<>(orderId, HttpStatus.CREATED);
    }

    @GetMapping("/getOrderStatus/{id}")
    public ResponseEntity<String> getOrderStatus(@PathVariable String id){
        return new ResponseEntity<>(orderService.getOrderStatus(id), HttpStatus.OK);
    }

    @PutMapping("/updateOrder")
    public ResponseEntity<String> updateOrder(@RequestBody OrderCreateDto orderDto){
        return new ResponseEntity<>(orderService.updateOrder(orderDto), HttpStatus.OK);
    }
}
