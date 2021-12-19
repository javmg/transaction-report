package com.jmgits.transactionreport.security.resolver;

import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;

import java.util.Date;

import static io.jsonwebtoken.SignatureAlgorithm.HS512;

@RequiredArgsConstructor
public class TokenHandler {

    public static final int TOKEN_VALIDITY = 4 * 60 * 60;

    private final String secret;

    public String generateToken(String subject) {

        var start = System.currentTimeMillis();

        return Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(new Date(start))
                .setExpiration(new Date(start + 1000 * TOKEN_VALIDITY))
                .signWith(HS512, secret)
                .compact();
    }

    public String getSubjectFromToken(String token) {

        var claims = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }
}
