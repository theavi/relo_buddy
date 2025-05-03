package com.rlb.oc.controller;

import com.rlb.oc.OrderCreateEvent;
import com.rlb.oc.service.OrderService;
import com.rlb.oc.model.Role;
import com.rlb.oc.model.User;
import com.rlb.oc.repository.RoleRepository;
import com.rlb.oc.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

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
    public ResponseEntity<String> placeOrder(@RequestBody OrderCreateEvent event) {
       // String result = orderService.placeOrder(event);
        Role admin=new Role();
        admin.setName("Admin");

        Role qa=new Role();
        qa.setName("QA");

        User user=new User();
        user.setName("Avinashs");
        user.setRoles(Arrays.asList(admin,qa));

        userRepository.save(user);
        return new ResponseEntity<String>("result", HttpStatus.CREATED);
    }
}
