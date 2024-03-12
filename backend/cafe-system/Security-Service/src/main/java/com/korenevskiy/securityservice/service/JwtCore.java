package com.korenevskiy.securityservice.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class JwtCore {

    @Value("${security.jwt.secret}")
    private String secret;

    @Value("${security.jwt.lifetime}")
    private long lifetime;

    public String generateToken(UserDetails userDetails) {
        Map<String, String> claims = new HashMap<>();
        String commaSeparatedListOfAuthorities = userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        claims.put("Authorities", commaSeparatedListOfAuthorities);

        return createToken(claims, userDetails.getUsername());
    }

    private String createToken(Map<String, String> claims, String username) {
        SecretKey key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        return Jwts.builder()
                .subject(username)
                .claims(claims)
                .issuer("security-service")
                .issuedAt(getIssuedAt())
                .expiration(getExpiration())
                .signWith(key)
                .compact();
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public String extractAuthorities(String token) {

        Function<Claims, String> authoritiesExtractor =
                (claims) -> (String) claims.get("Authorities");

        return extractClaim(token, authoritiesExtractor);
    }

    private String extractClaim(String token, Function<Claims, String> claimsResolver) {
        final Claims claims = extractClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractClaims(String token) {
        SecretKey key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private Date getIssuedAt() {
        return new Date(System.currentTimeMillis());
    }

    private Date getExpiration() {
        return new Date(System.currentTimeMillis() + lifetime);
    }

}
