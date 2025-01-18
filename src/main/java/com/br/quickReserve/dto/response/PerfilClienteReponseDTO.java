package com.br.quickReserve.dto.response;

import java.time.LocalDate;

public record PerfilClienteReponseDTO(
    Long id,
    String nome,
    String cpf,
    String email,
    LocalDate dataAniversario
) {}
