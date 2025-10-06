package com.rlb.auth.service.impl;

import com.netflix.discovery.converters.Auto;
import com.rlb.auth.dto.AuthRequestDto;
import com.rlb.auth.dto.UserDto;
import com.rlb.auth.repository.AuthRepository;
import com.rlb.auth.service.AuthService;
import mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class IAuthService implements AuthService {

    @Autowired
    private AuthRepository authRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    public ResponseEntity<String> createUser(UserDto dto){
        dto.setPassword(passwordEncoder.encode(dto.getPassword()));
        authRepository.save(UserMapper.toEntity(dto));
        return new ResponseEntity<>("User Created", HttpStatus.CREATED);
    }

    public ResponseEntity<String> getToken(String userName){
        String token = jwtService.generateToken(userName);
        return new ResponseEntity<>(token, HttpStatus.CREATED);
    }

    public ResponseEntity<String> validateToken(String token){
        jwtService.validateToken(token);
        return new ResponseEntity<>("token is valid", HttpStatus.CREATED);
    }

}
