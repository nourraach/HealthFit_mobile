package com.project.api.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {
    
    @Value("${jwt.secret}")
    private String secret;
    
    @Value("${jwt.expiration}")
    private Long expiration;
    
    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }
    
    public String generateToken(String adresseEmail) {
        return Jwts.builder()
                .setSubject(adresseEmail)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }
    
    public String generateToken(String adresseEmail, Long userId) {
        return Jwts.builder()
                .setSubject(adresseEmail)
                .claim("userId", userId)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }
    
    public String extractEmail(String token) {
        try {
            String email = extractClaims(token).getSubject();
            System.out.println("JwtUtil - Extracted email: " + email);
            return email;
        } catch (Exception e) {
            System.err.println("JwtUtil - Error extracting email: " + e.getMessage());
            throw e;
        }
    }
    
    public Long extractUserId(String token) {
        try {
            Claims claims = extractClaims(token);
            Object userIdObj = claims.get("userId");
            if (userIdObj != null) {
                return Long.valueOf(userIdObj.toString());
            }
            return null;
        } catch (Exception e) {
            System.err.println("JwtUtil - Error extracting userId: " + e.getMessage());
            return null;
        }
    }
    
    public boolean isTokenValid(String token, String adresseEmail) {
        try {
            final String email = extractEmail(token);
            boolean isExpired = isTokenExpired(token);
            boolean emailMatches = email.equals(adresseEmail);
            
            System.out.println("JwtUtil - Token validation:");
            System.out.println("  Email from token: " + email);
            System.out.println("  Expected email: " + adresseEmail);
            System.out.println("  Email matches: " + emailMatches);
            System.out.println("  Is expired: " + isExpired);
            System.out.println("  Is valid: " + (emailMatches && !isExpired));
            
            return (emailMatches && !isExpired);
        } catch (Exception e) {
            System.err.println("JwtUtil - Error validating token: " + e.getMessage());
            return false;
        }
    }
    
    private boolean isTokenExpired(String token) {
        Date expiration = extractClaims(token).getExpiration();
        Date now = new Date();
        System.out.println("JwtUtil - Expiration check:");
        System.out.println("  Token expires at: " + expiration);
        System.out.println("  Current time: " + now);
        return expiration.before(now);
    }
    
    private Claims extractClaims(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            System.err.println("JwtUtil - Error parsing token: " + e.getMessage());
            throw e;
        }
    }
}
