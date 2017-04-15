package com.wpam.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.Optional;

import static io.jsonwebtoken.Claims.ISSUER;
import static io.jsonwebtoken.Claims.SUBJECT;
import static io.jsonwebtoken.Header.JWT_TYPE;
import static io.jsonwebtoken.Header.TYPE;
import static io.jsonwebtoken.SignatureAlgorithm.HS256;
import static java.util.Optional.ofNullable;

@Component
public class JwtTokenUtil {

    @Value("${jwt.secret}")
    private String secret;

    Optional<String> getUsernameFromToken(String token) {
        try {
            Optional<Claims> claims = getClaimsFromToken(token);
            return claims.isPresent() ? ofNullable(claims.get().getSubject()) : Optional.empty();
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }

    Boolean validateToken(String token, UserDetails userDetails) {
        Optional<String> username = getUsernameFromToken(token);
        return username.map(s -> s.equals(userDetails.getUsername())).orElse(false);
    }

    public String generateToken(UserDetails user) {
        try {
            return Jwts.builder()
                    .claim(SUBJECT, user.getUsername())
                    .claim(ISSUER, "gimli")
                    .setHeaderParam(TYPE, JWT_TYPE)
                    .signWith(HS256, secret.getBytes("UTF-8"))
                    .compact();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Optional<Claims> getClaimsFromToken(String token) throws IllegalArgumentException {
        Claims claims = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
        return ofNullable(claims);
    }
}
