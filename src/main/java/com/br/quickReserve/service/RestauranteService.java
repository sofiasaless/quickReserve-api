package com.br.quickReserve.service;

import org.springframework.stereotype.Service;

import com.br.quickReserve.model.RestauranteEntity;
import com.br.quickReserve.repository.RestauranteRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RestauranteService {
    
    private final RestauranteRepository restauranteRepository;

    public RestauranteEntity salvarRestaurante(RestauranteEntity restauranteEntity) {
        return this.restauranteRepository.save(restauranteEntity);
    }

}
