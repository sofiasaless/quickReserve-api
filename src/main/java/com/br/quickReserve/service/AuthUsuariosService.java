package com.br.quickReserve.service;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.br.quickReserve.dto.request.AuthRequestDTO;
import com.br.quickReserve.model.enums.TipoUsuario;
import com.br.quickReserve.repository.ClienteRepository;
import com.br.quickReserve.repository.RestauranteRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthUsuariosService {

    private final ClienteRepository clienteRepository;

    private final RestauranteRepository restauranteRepository;

    private final PasswordEncoder passwordEncoder;

    @Value("${security.token.secret}")
    private String secretKey;

    public String entrarComoCliente(AuthRequestDTO authRequestDTO) throws AuthenticationException {
        // verificando a existência do cliente
        var cliente = this.clienteRepository.findByEmail(authRequestDTO.getEmail()).orElseThrow(() -> {
            throw new UsernameNotFoundException("Cliente não encontrado na base de dados!");
        });

        // verificar a senha
        var passwordMatches = this.passwordEncoder.matches(authRequestDTO.getSenha(), cliente.getSenha());
        // se as senhas não derem match
        if (!passwordMatches) {
            throw new AuthenticationException();
        }
        // se as senhas derem match
        Algorithm algorithm = Algorithm.HMAC256(secretKey); // passar uma secret que ninguem tenha acesso
        var token = JWT.create()
            .withIssuer("upbusiness")
            .withExpiresAt(Instant.now().plus(Duration.ofHours(2))) // passando o tempo de expiração do token
            .withSubject(cliente.getId().toString()) // passando uma informação unica da entidade
            .withClaim("roles", Arrays.asList("CLIENTE")) // setando as roles do usuario
            .sign(algorithm) // passando o algoritmo de criação do token
        ;
        return token;
    }

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
            .withIssuer("upbusiness")
            .withExpiresAt(Instant.now().plus(Duration.ofHours(2)))
            .withSubject(restaurante.getId().toString()) // passando uma informação unica da entidade
            .withClaim("roles", Arrays.asList("RESTAURANTE"))
            .sign(algorithm) // passando o algoritmo de criação do token
        ;
        return token;
    }

    public void verificarStatusUsuario(Long id, TipoUsuario tipoUsuario) {
        if (tipoUsuario == TipoUsuario.CLIENTE) {
            this.clienteRepository.findById(id).orElseThrow(() -> {
                throw new UsernameNotFoundException("Cliente não logado!");
            });
            return;
        }
        
        this.restauranteRepository.findById(id).orElseThrow(() -> {
            throw new UsernameNotFoundException("Restaurante não logado!");
        });

    }
}
