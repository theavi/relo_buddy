package com.rlb.notification.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NotificationController {


    @GetMapping("/show")
    public ResponseEntity<String> provider() {
        return new ResponseEntity<>("success", HttpStatus.OK);
    }
}
