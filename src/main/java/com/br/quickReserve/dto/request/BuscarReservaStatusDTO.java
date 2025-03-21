package com.br.quickReserve.dto.request;

import com.br.quickReserve.model.enums.StatusReserva;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public record BuscarReservaStatusDTO(
    
    @Enumerated(EnumType.STRING)
    StatusReserva statusReserva

){};
