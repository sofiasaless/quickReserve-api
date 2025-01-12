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
import com.br.quickReserve.dto.request.ClienteRequestDTO;
import com.br.quickReserve.exception.ClienteJaCadastradoException;
import com.br.quickReserve.model.ClienteEntity;
import com.br.quickReserve.repository.ClienteRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClienteService {
    
    private final ClienteRepository clienteRepository;

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
            .withIssuer("upbusiness") //
            .withSubject(cliente.getId().toString()) // passando uma informação unica da entidade
            .sign(algorithm) // passando o algoritmo de criação do token
        ;
        return token;
    }

    public ClienteEntity salvarCliente(ClienteRequestDTO clienteRequestDTO) {
        this.clienteRepository.findByCpfOrEmail(clienteRequestDTO.getCpf(), clienteRequestDTO.getEmail()).ifPresent((cli) -> {
            throw new ClienteJaCadastradoException();
        });
        
        var entidadeCliente = ClienteEntity.builder()
            .nome(clienteRequestDTO.getNome())
            .cpf(clienteRequestDTO.getCpf())
            .email(clienteRequestDTO.getEmail())
            .senha(passwordEncoder.encode(clienteRequestDTO.getSenha()))
            .dataAniversario(clienteRequestDTO.getDataAniversario())
        .build();
        return this.clienteRepository.save(entidadeCliente);
    }

    public List<ClienteEntity> listarTodosClientes() {
        return this.clienteRepository.findAll();
    }

}
