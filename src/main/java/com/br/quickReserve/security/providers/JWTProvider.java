package com.br.quickReserve.security.providers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

@Service
public class JWTProvider {

    @Value("${security.token.secret}")
    private String secretKey;

    public String validarToken(String token) {
        // o token chega com o prefixo bearer, então é necessário fazer replace por ""
        token = token.replace("Bearer ", "");

        Algorithm algoritimo = Algorithm.HMAC256(secretKey);

        try {
            // o subject vai ser o que foi passado no campo subject quando aconteceu a criação do token, no caso, será o id dos clientes e restaurantes
            var subject = JWT.require(algoritimo)
            .build()
            .verify(token)
            .getSubject();

            return subject;
        } catch (Exception e) {
            return "";
        }

    }
}
