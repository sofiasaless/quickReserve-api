package com.br.quickReserve.service;

import org.springframework.stereotype.Service;

import com.br.quickReserve.model.MesaEntity;
import com.br.quickReserve.repository.MesaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MesaService {
    
    private final MesaRepository mesaRepository;

    public MesaEntity salvarMesa(MesaEntity mesaEntity) {
        return this.mesaRepository.save(mesaEntity);
    }

}
