package com.br.quickReserve.exception.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BadRequestDTO {
    private String mensagem;
}
