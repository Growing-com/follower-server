package com.sarangchurch.follower.auth.security;

import com.sarangchurch.follower.auth.domain.model.LoginMember;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;

@Slf4j
public class JwtUtils {
    private static final String ROLE_KEY = "role";

    @Value("${jwt.secret}")
    private String jwtSecret;
    @Value("${jwt.access-token-valid-time-in-milliseconds}")
    private long accessTokenExpirationMs;

    public String generateAccessToken(LoginMember loginMember) {
        return Jwts.builder()
                .claim(ROLE_KEY, loginMember.getRoleName())
                .setSubject(String.valueOf(loginMember.getId()))
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + accessTokenExpirationMs))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public void validateToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(jwtSecret)
                    .parseClaimsJws(token);
        } catch (SignatureException e) {
            log.error("Invalid JWT signature: {}", e.getMessage());
            throw e;
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token: {}", e.getMessage());
            throw e;
        } catch (ExpiredJwtException e) {
            log.error("JWT token is expired: {}", e.getMessage());
            throw e;
        } catch (UnsupportedJwtException e) {
            log.error("JWT token is unsupported: {}", e.getMessage());
            throw e;
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty: {}", e.getMessage());
            throw e;
        }
    }

    public String extractUserId(String token) {
        return Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}
