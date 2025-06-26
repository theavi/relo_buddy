package com.rlb.api.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JWTTokenProvider {

    private long expireAt = 3600000;
    private String jwtSecret="dqk1OsLhkEial8as4CedfUlRhtXHGq9C";

    public String generateToken(Authentication authentication) {
        String username = authentication.getName();
        Date createDate=new Date();
        Date expireDate=new Date(createDate.getTime()+expireAt);

        return Jwts.builder().subject(username).issuedAt(createDate).expiration(expireDate).signWith(key(), SignatureAlgorithm.HS256).compact();
    }


    private Key key(){
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }
}
