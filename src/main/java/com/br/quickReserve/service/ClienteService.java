package com.br.quickReserve.service;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
