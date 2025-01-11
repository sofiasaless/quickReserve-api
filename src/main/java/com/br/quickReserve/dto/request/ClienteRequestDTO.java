package com.br.quickReserve.dto.request;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClienteRequestDTO {
    
    private String nome;
    private String cpf;
    private String email;
    private String senha;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataAniversario;

}
