package com.br.quickReserve.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.br.quickReserve.model.enums.StatusMesa;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "mesas")
@Data
public class MesaEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int numero;

    @ManyToOne // posso ter varias mesas para um restaurante, mas uma mesa só pode ter um restaurante
    @JoinColumn(name = "restaurante_id", insertable = false, updatable = false)
    private RestauranteEntity restauranteEntity;

    @Column(name = "restaurante_id", nullable = false)
    private Long restauranteId;

    @Enumerated(EnumType.STRING)
    private StatusMesa statusMesa;
    
    @CreationTimestamp
    private LocalDateTime criadoEm;
    
}
