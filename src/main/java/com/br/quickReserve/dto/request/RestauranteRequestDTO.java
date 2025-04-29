package com.br.quickReserve.dto.request;

import org.hibernate.validator.constraints.URL;

import com.br.quickReserve.model.enums.TipoRestaurante;

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
    
    private String descricao;
    
    @URL
    private String imagemPerfil;

    @URL
    private String imagemCapa;

    private TipoRestaurante tipoRestaurante;
    
    private String email;
    
    private String senha;

}
