package com.br.quickReserve.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.br.quickReserve.dto.request.RestauranteRequestDTO;
import com.br.quickReserve.model.RestauranteEntity;
import com.br.quickReserve.repository.RestauranteRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RestauranteService {
    
    private final RestauranteRepository restauranteRepository;

    public RestauranteEntity salvarRestaurante(RestauranteRequestDTO restauranteRequestDTO) {
        var entidadeRestaurante = RestauranteEntity.builder()
            .nome(restauranteRequestDTO.getNome())
            .cnpj(restauranteRequestDTO.getCnpj())
            .email(restauranteRequestDTO.getEmail())
            .senha(restauranteRequestDTO.getSenha())
        .build();
        return this.restauranteRepository.save(entidadeRestaurante);
    }

    public List<RestauranteEntity> listarTodosRestaurantes() {
        return this.restauranteRepository.findAll();
    }

}
