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

    public MesaEntity salvarMesa(MesaRequestDTO mesaRequestDTO, Long restauranteId) {
        var entidadeMesa = MesaEntity.builder()
            .numero(mesaRequestDTO.getNumero())
            .capacidadePessoas(mesaRequestDTO.getCapacidadePessoas())
            .restauranteId(restauranteId)
        .build();
        
        return this.mesaRepository.save(entidadeMesa);
    }

    public List<MesaEntity> listarMesasPorEstabelecimento(Long id){
        
        // alternativa para evitar a exposição de informações senvíveis da entidade restaurante, setei o restauranteEntity como null, por enquanto foi a alternativa que achei mais viável
        List<MesaEntity> listaMesas = this.mesaRepository.findByRestauranteId(id);
        listaMesas.stream().forEach(mesa -> mesa.setRestauranteEntity(null));

        return listaMesas;
    }

}
