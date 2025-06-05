package com.rlb.api.security;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JWTTokenProvider {

    private long expireAt = 3600000;

    public String generateToken(Authentication authentication) {
        String username = authentication.getName();
        Date currentDate=new Date();
        Date expireDate=new Date(currentDate.getTime()+expireAt);

        //String token=Jwts
        return "";
    }
}
