package com.br.quickReserve.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.br.quickReserve.dto.request.BuscarReversaRequestDTO;
import com.br.quickReserve.dto.request.ReservaRequestDTO;
import com.br.quickReserve.dto.request.ReservaUpdateRequestDTO;
import com.br.quickReserve.exception.AlteracaoNaoAutorizadaException;
import com.br.quickReserve.exception.MesaNaoDisponivelException;
import com.br.quickReserve.exception.ReservaNaoEncontradaException;
import com.br.quickReserve.model.ReservaEntity;
import com.br.quickReserve.model.enums.StatusReserva;
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

        var entidadeReserva = ReservaEntity.builder()
            .clienteId(reservaRequestDTO.getClienteId())
            .mesaId(reservaRequestDTO.getMesaId())
            .dataParaReserva(reservaRequestDTO.getDataParaReserva())
            .statusReserva(reservaRequestDTO.getStatusReserva())
        .build();
        
        return this.reservaRepository.save(entidadeReserva);
    }

    public ReservaEntity encontrarReservaPorMesaEData(BuscarReversaRequestDTO buscarReversaRequestDTO) {
        return this.reservaRepository.findByDataParaReservaAndMesaId(buscarReversaRequestDTO.dataParaReserva(), buscarReversaRequestDTO.mesaId()).orElse(new ReservaEntity());
    }
    
    public List<ReservaEntity> encontrarReservaPorMesa(Long id, String statusReserva) {
        return this.reservaRepository.findByMesaIdAndStatusReserva(id, StatusReserva.valueOf(statusReserva));
    }

    public List<ReservaEntity> encontrarReservaPorRestaurante(Long id, String statusReserva) {
        return this.reservaRepository.findByRestauranteId(id, statusReserva);
    }

    public List<ReservaEntity> listarTodasReservas() {
        return this.reservaRepository.findAll();
    }

    public ReservaEntity atualizarReserva(ReservaUpdateRequestDTO reservaUpdateRequestDTO) {

        ReservaEntity reservaDesatualizada = this.reservaRepository.findById(reservaUpdateRequestDTO.getId()).orElseThrow(() -> {
            throw new ReservaNaoEncontradaException();
        });

        reservaDesatualizada.setMesaId(reservaUpdateRequestDTO.getMesaId());
        reservaDesatualizada.setDataParaReserva(reservaUpdateRequestDTO.getDataParaReserva());
        reservaDesatualizada.setStatusReserva(reservaUpdateRequestDTO.getStatusReserva());

        return this.reservaRepository.save(reservaDesatualizada);
    }

    public ReservaEntity atualizarReservaViaCliente(Long id, ReservaUpdateRequestDTO reservaUpdateRequestDTO, Long clienteId) {
        ReservaEntity reservaDesatualizada = this.reservaRepository.findByIdAndClienteId(id, clienteId).orElseThrow(() -> {
            throw new ReservaNaoEncontradaException();
        });

        if (reservaDesatualizada.getStatusReserva() == StatusReserva.CONFIRMADA || reservaDesatualizada.getStatusReserva() == StatusReserva.CANCELADA) {
            throw new AlteracaoNaoAutorizadaException();
        }

        reservaDesatualizada.setMesaId(reservaUpdateRequestDTO.getMesaId());
        reservaDesatualizada.setDataParaReserva(reservaUpdateRequestDTO.getDataParaReserva());
        
        return this.reservaRepository.save(reservaDesatualizada);
    }

}
