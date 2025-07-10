package com.rlb.api.controller;

import com.rlb.api.dto.AuthResponseDto;
import com.rlb.api.dto.LoginDto;
import com.rlb.api.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody LoginDto dto) {
        System.out.println("HTTP Post initiated :: Login");
        //Generate token from service after login success
        String token = authService.login(dto);
        //set token response
        AuthResponseDto authRes = new AuthResponseDto();
        authRes.setJwtToken(token);
        return new ResponseEntity<>(authRes, HttpStatus.OK);
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello";
    }
}

