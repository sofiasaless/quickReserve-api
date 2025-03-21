package com.br.quickReserve.dto.response;

import java.time.LocalDateTime;

public record PerfilRestauranteReponseDTO(
    Long id,
    String nome,
    String cnpj,
    String email,
    LocalDateTime criadoEm
) {}
