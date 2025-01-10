package com.br.quickReserve.service;

import org.springframework.stereotype.Service;

import com.br.quickReserve.dto.BuscarReversaRequestDTO;
import com.br.quickReserve.exception.MesaNaoDisponivelException;
import com.br.quickReserve.model.ReservaEntity;
import com.br.quickReserve.repository.ReservaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReservaService {
    
    private final ReservaRepository reservaRepository;

    public ReservaEntity salvarReserva(ReservaEntity reservaEntity) {
        this.reservaRepository.findByDataParaReservaAndMesaId(reservaEntity.getDataParaReserva(), reservaEntity.getMesaId()).ifPresent(exc -> {
            throw new MesaNaoDisponivelException("A mesa não está disponível para a data selecionada!");
        });
        
        return this.reservaRepository.save(reservaEntity);
    }

    public ReservaEntity encontrarReserva(BuscarReversaRequestDTO buscarReversaRequestDTO) {
        return this.reservaRepository.findByDataParaReservaAndMesaId(buscarReversaRequestDTO.dataParaReserva(), buscarReversaRequestDTO.mesaId()).orElse(new ReservaEntity());
    }

}
