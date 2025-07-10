package com.rlb.api;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Encoders;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.crypto.SecretKey;

@SpringBootApplication
public class RlbAPIGatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(RlbAPIGatewayApplication.class);
        SecretKey key = Jwts.SIG.HS256.key().build();
        String base64Key = Encoders.BASE64.encode(key.getEncoded());
        System.out.println("Your JWT key:\n" + base64Key);
        System.out.println("RLB API Gateway Started.");
    }
}