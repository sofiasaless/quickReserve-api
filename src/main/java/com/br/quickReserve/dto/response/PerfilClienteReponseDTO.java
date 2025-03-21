package com.br.quickReserve.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record PerfilClienteReponseDTO(
    Long id,
    String nome,
    String cpf,
    String email,
    LocalDate dataAniversario,
    LocalDateTime criadoEm
) {}
