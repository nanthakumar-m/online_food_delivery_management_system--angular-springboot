package com.cts.OnlineDeliverySystem.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256); // Secret key for signing JWTs
    private final long expirationTime = 1000 * 60 * 60 * 10; // 10 hours

    // Generate a JWT token
    public String generateToken(String email, String role) {
        return Jwts.builder()
                .setSubject(email)
                .claim("role", role) // Add role as a claim
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(key)
                .compact();
    }

    // Extract email (subject) from the token
    public String extractEmail(String token) {
        return Jwts.parser().setSigningKey(key).build().parseClaimsJws(token).getBody().getSubject();
    }

    // Extract role from the token
    public String extractRole(String token) {
        return (String) Jwts.parser().setSigningKey(key).build().parseClaimsJws(token).getBody().get("role");
    }

    // Validate the token
    public boolean validateToken(String token, String email) {
        final String extractedEmail = extractEmail(token);
        return (extractedEmail.equals(email) && !isTokenExpired(token));
    }

    // Check if the token is expired
    private boolean isTokenExpired(String token) {
        return Jwts.parser().setSigningKey(key).build().parseClaimsJws(token).getBody().getExpiration().before(new Date());
    }

}