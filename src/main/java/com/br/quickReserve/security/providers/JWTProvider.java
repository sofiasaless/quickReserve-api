package com.br.quickReserve.security.providers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

@Service
public class JWTProvider {

    @Value("${security.token.secret}")
    private String secretKey;

    // mudei o retorno do metodo para retornar um jwt decodificado, assim pegando todos os atributos de uma vez
    // .... ao invés de pegar apenas o subject
    public DecodedJWT validarToken(String token) {
        // o token chega com o prefixo bearer, então é necessário fazer replace por ""
        token = token.replace("Bearer ", "");
        
        Algorithm algorithm = Algorithm.HMAC256(secretKey);

        try {
            var tokenDecoded = JWT.require(algorithm)
            .build()
            .verify(token);
            return tokenDecoded;
        } catch (JWTVerificationException e) {
            // e.printStackTrace();
            return null;
        }
    }

}
