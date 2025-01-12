package com.br.quickReserve.service;

import java.util.List;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.br.quickReserve.dto.request.AuthRequestDTO;
import com.br.quickReserve.dto.request.RestauranteRequestDTO;
import com.br.quickReserve.exception.RestauranteJaCadastradoException;
import com.br.quickReserve.model.RestauranteEntity;
import com.br.quickReserve.repository.RestauranteRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RestauranteService {
    
    private final RestauranteRepository restauranteRepository;

    private final PasswordEncoder passwordEncoder;

    @Value("${security.token.secret}")
    private String secretKey;

    public String entrarComoRestaurante(AuthRequestDTO authRequestDTO) throws AuthenticationException {
        // verificando a existência do cliente
        var restaurante = this.restauranteRepository.findByEmail(authRequestDTO.getEmail()).orElseThrow(() -> {
            throw new UsernameNotFoundException("Restaurante não encontrado na base de dados!");
        });

        // verificar a senha
        var passwordMatches = this.passwordEncoder.matches(authRequestDTO.getSenha(), restaurante.getSenha());
        // se as senhas não derem match
        if (!passwordMatches) {
            throw new AuthenticationException();
        }
        // se as senhas derem match
        Algorithm algorithm = Algorithm.HMAC256(secretKey); // passar uma secret que ninguem tenha acesso
        var token = JWT.create()
            .withIssuer("upbusiness") //
            .withSubject(restaurante.getId().toString()) // passando uma informação unica da entidade
            .sign(algorithm) // passando o algoritmo de criação do token
        ;
        return token;
    }

    public RestauranteEntity salvarRestaurante(RestauranteRequestDTO restauranteRequestDTO) {
        this.restauranteRepository.findByCnpjOrEmail(restauranteRequestDTO.getCnpj(), restauranteRequestDTO.getEmail()).ifPresent((user) -> {
            throw new RestauranteJaCadastradoException();
        });
        
        var entidadeRestaurante = RestauranteEntity.builder()
            .nome(restauranteRequestDTO.getNome())
            .cnpj(restauranteRequestDTO.getCnpj())
            .email(restauranteRequestDTO.getEmail())
            .senha(passwordEncoder.encode(restauranteRequestDTO.getSenha()))
        .build();
        return this.restauranteRepository.save(entidadeRestaurante);
    }

    public List<RestauranteEntity> listarTodosRestaurantes() {
        return this.restauranteRepository.findAll();
    }

}
