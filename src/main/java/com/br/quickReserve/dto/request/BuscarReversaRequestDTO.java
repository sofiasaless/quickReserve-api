package com.br.quickReserve.dto.request;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

public record BuscarReversaRequestDTO(
    
    Long mesaId,
    @JsonFormat(pattern = "yyyy-MM-dd")
    LocalDate dataParaReserva

){}