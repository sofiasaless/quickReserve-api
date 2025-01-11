package com.br.quickReserve.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RestauranteRequestDTO {

    private String nome;
    private String cnpj;
    private String email;
    private String senha;

}
