package com.rlb.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class ProducerController {

    @Value("${server.port}")
    private int port;

    @GetMapping
    public ResponseEntity<String> hello() {
        System.out.println("HTTP GET Request for Producer started : ");
        return new ResponseEntity<>(String.format("Hello from !!"+port), HttpStatus.OK);
    }
}
