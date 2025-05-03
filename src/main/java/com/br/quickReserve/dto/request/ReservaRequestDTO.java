package com.br.quickReserve.dto.request;

import java.time.LocalDate;

import com.br.quickReserve.model.enums.StatusReserva;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReservaRequestDTO {

    private Long mesaId;
    private Long clienteId;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataParaReserva;

    private String nomeParaReserva;

    private int quantidadePessoas;

    private String observacoes;

    @Builder.Default
    private StatusReserva statusReserva = StatusReserva.PENDENTE;
    
}
