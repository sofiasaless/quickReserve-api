package com.br.quickReserve.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.br.quickReserve.model.enums.TipoRestaurante;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "restaurantes")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RestauranteEntity {
  
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String nome;

    private String descricao;

    @Enumerated(EnumType.STRING)
    private TipoRestaurante tipoRestaurante;

    private String imagemPerfil;

    private String imagemCapa;

    @Column(unique = true)
    private String cnpj;

    @Column(unique = true)
    private String email;

    private String senha;

    @CreationTimestamp
    private LocalDateTime criadoEm;
}
