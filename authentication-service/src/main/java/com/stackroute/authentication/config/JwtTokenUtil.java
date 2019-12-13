package com.stackroute.authentication.config;


import com.stackroute.authentication.domain.LoginDTO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;


@Component
public class JwtTokenUtil implements Serializable {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.token.validity}")
    private int validity;

    @Autowired
    private AesCipher aesCipher;

    //generate token for user
    public String generateToken(LoginDTO loginDTO) {
        Claims claims = Jwts.claims()
                        .setSubject(loginDTO.getEmail())
                        .setIssuedAt(new Date(System.currentTimeMillis()))
                        .setExpiration(new Date(System.currentTimeMillis() + validity *3600000))
                        .setIssuer("Authentication-service");
//        claims.put("role",userDetails.getPassword());
        return aesCipher.encrypt(Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS512, secret).compact());
    }
}