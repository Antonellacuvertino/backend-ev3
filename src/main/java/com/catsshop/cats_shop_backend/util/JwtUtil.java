package com.catsshop.cats_shop_backend.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.Map; // 👈 NECESARIO PARA EL MAP DE CLAIMS

@Component
public class JwtUtil {

    private final Key key;
    private final long expiration;

    public JwtUtil(
            @Value("${app.jwt.secret}") String secret,
            @Value("${app.jwt.expiration}") long expiration
    ) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
        this.expiration = expiration;
    }

    /**
     * Genera un token JWT incluyendo claims personalizados.
     * @param claims Mapa de claims (ej: userId, role)
     * @param subject El sujeto principal (username)
     * @return El JWT generado
     */
    // ⚠️ MÉTODO CORREGIDO: ACEPTA MAP Y STRING ⚠️
    public String generateToken(Map<String, Object> claims, String subject) {
        Date now = new Date();
        Date exp = new Date(now.getTime() + expiration);

        return Jwts.builder()
                .setClaims(claims)           // Usa el mapa de claims
                .setSubject(subject)         // Usa el username como sujeto
                .setIssuedAt(now)
                .setExpiration(exp)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    // Los demás métodos (getClaims, validate, getUsername, getRole) permanecen igual
    // ya que dependen de los claims que ahora están incluidos en el token.

    public Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean validate(String token) {
        try {
            getClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String getUsername(String token) {
        return getClaims(token).getSubject();
    }

    // Nota: Si el rol es un String, este método funciona.
    // Si hubieras usado .claim("role", role) en el generador, seguiría funcionando.
    public String getRole(String token) {
        return (String) getClaims(token).get("role");
    }
}