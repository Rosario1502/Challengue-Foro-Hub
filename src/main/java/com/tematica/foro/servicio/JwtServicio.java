package com.tematica.foro.servicio;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

/** Servicio para generar y leer tokens JWT. */
@Service
public class JwtServicio {
    private final Key clave;
    private final long expiracionMs;

    public JwtServicio(@Value("${app.jwt.secret}") String secreto,
                       @Value("${app.jwt.expiration-ms}") long expiracionMs) {
        this.clave = Keys.hmacShaKeyFor(secreto.getBytes());
        this.expiracionMs = expiracionMs;
    }

    public String generarToken(String nombreUsuario) {
        Date ahora = new Date();
        Date exp = new Date(ahora.getTime() + expiracionMs);
        return Jwts.builder()
                .setSubject(nombreUsuario)
                .setIssuedAt(ahora)
                .setExpiration(exp)
                .signWith(clave, SignatureAlgorithm.HS256)
                .compact();
    }

    public String extraerNombreUsuario(String token) {
        return Jwts.parserBuilder().setSigningKey(clave).build()
                .parseClaimsJws(token).getBody().getSubject();
    }
}
