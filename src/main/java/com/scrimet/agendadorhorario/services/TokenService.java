package com.scrimet.agendadorhorario.services;


import com.scrimet.agendadorhorario.infrainstructure.entities.Usuario;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TokenService {
    private final String secretKeyString = "minha-chave-secreta-para-jwt-2026";
    private final SecretKey key = Keys.hmacShaKeyFor(secretKeyString.getBytes());
    public String gerarToken(Usuario usuario){
        return Jwts.builder()
                .subject(usuario.getEmail())
                .issuedAt(new Date())
                .expiration(new Date())
                .signWith(key)
                .compact();
    }
    public String validarToken(String token){
        try{
            Claims claims = Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseClaimsJws(token)
                    .getPayload();
            return claims.getSubject();
        }catch(Exception e){
            return null;
        }
    }
}
