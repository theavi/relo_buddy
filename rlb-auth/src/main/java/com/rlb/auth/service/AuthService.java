package com.rlb.auth.service;

import com.rlb.auth.dto.AuthRequestDto;
import com.rlb.auth.dto.UserDto;
import org.springframework.http.ResponseEntity;

public interface AuthService{

    public ResponseEntity<String> createUser(UserDto dto);
    public ResponseEntity<String> getToken(String userName);
    public ResponseEntity<String> validateToken(String token);
}
