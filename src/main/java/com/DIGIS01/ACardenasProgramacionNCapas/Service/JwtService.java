/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.DIGIS01.ACardenasProgramacionNCapas.Service;

import com.DIGIS01.ACardenasProgramacionNCapas.DAO.UsuarioDAOJPAImplementation;
import com.DIGIS01.ACardenasProgramacionNCapas.JPA.Result;
import com.DIGIS01.ACardenasProgramacionNCapas.JPA.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

/**
 *
 * @author digis
 */
@Service
public class JwtService {
    
    @Autowired
    @Lazy
    UsuarioDAOJPAImplementation usuarioDAOJPAImplementation;

    private static final String SECRET__KEY = "$2a$12$mnKoNNIrH6vnx5Vu7qerae.H3lNslCFoYy3fDhzvqdJTekyaNsjsS";
    private static final long EXPIRATION_TIME = 3600000;

    private SecretKey getSignKey() {
        byte[] keyBytes = SECRET__KEY.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(UserDetails user) {
        Result result = usuarioDAOJPAImplementation.GetByUserName(user.getUsername());
        Usuario usuario = (Usuario) result.object;
        
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("role", user.getAuthorities());
        extraClaims.put("idUsuario", usuario.getIdUsuario());

        return Jwts.builder()
                .claims(extraClaims)
                .subject(user.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(getSignKey())
                .compact();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }
    
//    public String extractIdUsuario() {
//        return extractClaim("idUsuario", Claims::getId);
//    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = Jwts.parser()
                .verifyWith(getSignKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return claimsResolver.apply(claims);
    }

    private boolean isTokenExpired(String token) {
        return extractClaim(token, Claims::getExpiration).before(new Date());
    }

}
