package com.sensoritoys.ms_usuarios.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {

    // Llave secreta segura para firmar los tokens (mínimo 256 bits)
    private final String SECRET_KEY_STRING = "sensoritoys-clave-secreta-muy-segura-para-jwt-2026-xyz";
    private final SecretKey key = Keys.hmacShaKeyFor(SECRET_KEY_STRING.getBytes());

    // Generar Token JWT real
    public String generarToken(String email) {
        long expirationTimeMillis = 1000 * 60 * 60 * 10; // 10 horas de duración
        return Jwts.builder()
                .subject(email)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expirationTimeMillis))
                .signWith(key)
                .compact();
    }

    // Extraer el email desde el token
    public String extraerEmail(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return claims.getSubject();
    }

    // Validar si el token es correcto y no ha expirado
    public boolean validarToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}