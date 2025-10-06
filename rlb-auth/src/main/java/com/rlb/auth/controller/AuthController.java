package com.rlb.auth.controller;

import com.rlb.auth.dto.AuthRequestDto;
import com.rlb.auth.dto.UserDto;
import com.rlb.auth.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    public AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<String> createUser(@RequestBody UserDto userDto){
        return authService.createUser(userDto);
    }

    @PostMapping("/token")
    public ResponseEntity<String> getToken(@RequestBody AuthRequestDto authRequestDto){
        return authService.getToken(authRequestDto.getUsername());
    }

    @GetMapping("/validate")
    public ResponseEntity<String> validateToken(@RequestParam("token") String token){
        return authService.validateToken(token);
    }

}
