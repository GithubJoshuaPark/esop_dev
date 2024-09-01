package com.soro.esop.utils;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}") // 1_800_000 # 30 mins
    private Long expiration;

    @Value("${jwt.refreshExpire}") // 432_000_000 # 5 days in milliseconds
    private Long refreshExpiration;

    private Key getsigningKey() {
        byte[] keyBytes = secret.getBytes();
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userDetails.getUsername());
    }

    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)   // claims, like roles
                .setSubject(subject) // username (subject, ex: email)
                .setIssuedAt(new Date(System.currentTimeMillis())) // issued date
                .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000)) // expiration date, 30 mins
                .signWith(getsigningKey(), SignatureAlgorithm.HS256) // sign with secret key
                .compact(); // compact to string
    }

    private String createRefreshToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)   // claims, like roles
                .setSubject(subject) // username (subject, ex: email)
                .setIssuedAt(new Date(System.currentTimeMillis())) // issued date
                .setExpiration(new Date(System.currentTimeMillis() + refreshExpiration * 1000)) // expiration date, 5 days
                .signWith(getsigningKey(), SignatureAlgorithm.HS256) // sign with secret key
                .compact(); // compact to string
    }

    public String generateRefreshToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return createRefreshToken(claims, userDetails.getUsername());
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }


    public String extractUsername(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getsigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean isTokenExpired(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getsigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration()
                .before(new Date());
    }
}
