package com.br.quickReserve.service;

import java.util.List;

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

    public List<ClienteEntity> listarTodosClientes() {
        return this.clienteRepository.findAll();
    }

}
