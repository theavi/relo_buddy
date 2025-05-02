package com.rlb.oc.controller;

import com.rlb.oc.dto.OrderCreateDto;
import com.rlb.oc.service.OrderService;
import com.rlb.oc.repository.RoleRepository;
import com.rlb.oc.repository.UserRepository;
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

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;


    @PostMapping("/order")
    public ResponseEntity<String> placeOrder(@RequestBody OrderCreateDto orderCreateDto) {
       String result = orderService.placeOrder(orderCreateDto);
        return new ResponseEntity<String>("result", HttpStatus.CREATED);
    }
}
