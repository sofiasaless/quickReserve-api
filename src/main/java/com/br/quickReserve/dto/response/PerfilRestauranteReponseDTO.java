package com.br.quickReserve.dto.response;

public record PerfilRestauranteReponseDTO(
    Long id,
    String nome,
    String cnpj,
    String email
) {}
