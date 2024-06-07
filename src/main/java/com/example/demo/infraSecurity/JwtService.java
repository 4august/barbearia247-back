package com.example.demo.infraSecurity;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.demo.domain.Usuario;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class JwtService {

    private static String SEGREDO = "fLaSeGrEd0awesrdxctfyvgubhinjomkpl09oiuytdrrtufcvitydf6yr5tfkgvyudasrdtgyyukfytjdytdf";

    public String generateToken(Usuario user){
        try{
            Algorithm algorithm = Algorithm.HMAC256(SEGREDO);
            String token = JWT.create()
                    .withIssuer("auth-api")
                    .withSubject(user.getUsername())
                    .withExpiresAt(tokenValidTime())
                    .sign(algorithm);
            return token;
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Deu ruim ao gerar o token de acesso", exception);
        }
    }

    public String validateToken(String token){
        try {
            Algorithm algorithm = Algorithm.HMAC256(SEGREDO);
            return JWT.require(algorithm)
                    .withIssuer("auth-api")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException exception){
            return "";
        }
    }

    private Instant tokenValidTime() {
        return LocalDateTime.now().plusMonths(1).toInstant(ZoneOffset.of("-04:00"));
    }

}
