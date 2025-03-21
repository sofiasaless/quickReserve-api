package com.br.quickReserve.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.br.quickReserve.dto.request.BuscarReversaMesaRequestDTO;
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

    public ReservaEntity encontrarReservaPorMesaEData(BuscarReversaRequestDTO buscarReversaRequestDTO) {
        return this.reservaRepository.findByDataParaReservaAndMesaId(buscarReversaRequestDTO.dataParaReserva(), buscarReversaRequestDTO.mesaId()).orElse(new ReservaEntity());
    }

     public List<ReservaEntity> listarTodasReservas() {
        return this.reservaRepository.findAll();
    }

}
