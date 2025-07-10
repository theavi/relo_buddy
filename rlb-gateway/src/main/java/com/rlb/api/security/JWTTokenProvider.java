package com.rlb.api.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;

@Component
public class JWTTokenProvider {

    private long expireAt = 3600000;
    //private String jwtSecret = "V3ZCT1J5cUlBdkJjU2JoZ2tUd21TcEhpbkNRSXhDUG16TzI4VE53MkpWTT0=";
    private String jwtSecret ="8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918";

    public String generateToken(Authentication authentication) {
        String username = authentication.getName();
        Date createDate = new Date();
        Date expireDate = new Date(createDate.getTime() + expireAt);

        return Jwts.builder().subject(username).issuedAt(createDate).expiration(expireDate).signWith(key(), SignatureAlgorithm.HS256).compact();
    }


    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    public boolean validateToken(String token) {
        Jwts.parser().verifyWith((SecretKey) key()).build().parse(token);
        return true;
    }

    public String getUserName(String token) {
        return Jwts.parser().verifyWith((SecretKey) key()).build().parseSignedClaims(token).getPayload().getSubject();
    }
}
