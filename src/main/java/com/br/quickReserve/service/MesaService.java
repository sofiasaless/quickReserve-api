package com.br.quickReserve.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.br.quickReserve.dto.request.MesaRequestDTO;
import com.br.quickReserve.model.MesaEntity;
import com.br.quickReserve.repository.MesaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MesaService {
    
    private final MesaRepository mesaRepository;

    public MesaEntity salvarMesa(MesaRequestDTO mesaRequestDTO) {
        var entidadeMesa = MesaEntity.builder()
            .numero(mesaRequestDTO.getNumero())
            .restauranteId(mesaRequestDTO.getRestauranteId())
        .build();
        
        return this.mesaRepository.save(entidadeMesa);
    }

    public List<MesaEntity> listarMesasPorEstabelecimento(Long id){
        return this.mesaRepository.findByRestauranteId(id);
    }

}
