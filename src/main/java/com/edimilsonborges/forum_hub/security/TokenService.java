package com.edimilsonborges.forum_hub.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.edimilsonborges.forum_hub.models.Usuario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {
    @Value("${api.security.token.secret}")
    private String secret;
    public String gerarToken(Usuario usuario){
        try {
            Algorithm algoritimo = Algorithm.HMAC256(secret);
            return  JWT.create()
                    .withIssuer("API Forum Hub")
                    .withSubject(usuario.getEmail())
                    .withClaim("id", usuario.getId().toString())
                    .withExpiresAt(dataExpiracao())
                    .sign(algoritimo);

        } catch (JWTCreationException exception){
            return null;
        }
    }

    private Instant dataExpiracao() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }

    public String getSubject(String tokenJWT){
        try {
            Algorithm algoritimo = Algorithm.HMAC256(secret);
            return JWT.require(algoritimo)
                    .withIssuer("API Forum Hub")
                    .build()
                    .verify(tokenJWT)
                    .getSubject();

        } catch (JWTVerificationException exception){
            return null;
        }
    }
}
