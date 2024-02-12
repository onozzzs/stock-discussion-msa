package com.example.activity.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Slf4j
@Service
public class TokenProvider {
    @Value("${jwt.secret}")
    private String SECRET_KEY;

    @Value("${jwt.expiration}")
    private String EXPIRATION;

    public String create(String mail, String userId) {
        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .setSubject(userId)
                .setIssuer("hhproject-app")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() +
                        Long.parseLong(EXPIRATION)))
                .compact();
    }

    public String getUserId(HttpServletRequest request) {
        String token = resolveAccessToken(request);
        return validateAndGetUserId(token);
    }

    public String validateAndGetUserId(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    public Long getExpiration(String accessToken) {
        Date expiration = Jwts.parserBuilder().setSigningKey(SECRET_KEY)
                .build().parseClaimsJws(accessToken).getBody().getExpiration();

        long now = new Date().getTime();

        return (expiration.getTime() - now);
    }

    public void accessTokenSetHeader(String accessToken, HttpServletResponse response) {
        String headerValue = "Bearer " + accessToken;
        response.setHeader("Authorization", headerValue);
    }

    public void refreshTokenSetHeader(String refreshToken, HttpServletResponse response) {
        response.setHeader("Refresh", refreshToken);
    }

    public String resolveAccessToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring("Bearer ".length());
        }
        return null;
    }
}
