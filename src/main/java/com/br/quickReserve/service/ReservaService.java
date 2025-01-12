package com.br.quickReserve.service;

import org.springframework.stereotype.Service;

import com.br.quickReserve.dto.request.BuscarReversaRequestDTO;
import com.br.quickReserve.dto.request.ReservaRequestDTO;
import com.br.quickReserve.exception.MesaNaoDisponivelException;
import com.br.quickReserve.model.ReservaEntity;
import com.br.quickReserve.repository.ReservaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReservaService {
    
    private final ReservaRepository reservaRepository;

    public ReservaEntity salvarReserva(ReservaRequestDTO reservaRequestDTO) {
        this.reservaRepository.findByDataParaReservaAndMesaId(reservaRequestDTO.getDataParaReserva(), reservaRequestDTO.getMesaId()).ifPresent(exc -> {
            throw new MesaNaoDisponivelException("A mesa não está disponível para a data selecionada!");
        });

        var entidadeReversa = ReservaEntity.builder()
            .clienteId(reservaRequestDTO.getClienteId())
            .mesaId(reservaRequestDTO.getMesaId())
            .dataParaReserva(reservaRequestDTO.getDataParaReserva())
            .statusReserva(reservaRequestDTO.getStatusReserva())
        .build();
        
        return this.reservaRepository.save(entidadeReversa);
    }

    public ReservaEntity encontrarReserva(BuscarReversaRequestDTO buscarReversaRequestDTO) {
        return this.reservaRepository.findByDataParaReservaAndMesaId(buscarReversaRequestDTO.dataParaReserva(), buscarReversaRequestDTO.mesaId()).orElse(new ReservaEntity());
    }

}
