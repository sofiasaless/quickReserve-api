package com.br.quickReserve.service;

import org.springframework.stereotype.Service;

import com.br.quickReserve.model.ClienteEntity;
import com.br.quickReserve.repository.ClienteRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClienteService {
    
    private final ClienteRepository clienteRepository;

    public ClienteEntity salvarCliente(ClienteEntity clienteEntity) {
        return this.clienteRepository.save(clienteEntity);
    }

}
