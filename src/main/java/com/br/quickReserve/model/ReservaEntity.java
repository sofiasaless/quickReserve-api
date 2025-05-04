package com.br.quickReserve.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.br.quickReserve.model.enums.StatusReserva;
import com.fasterxml.jackson.annotation.JsonFormat;

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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "reservas")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReservaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "mesa_id", insertable = false, updatable = false)
    private MesaEntity mesaEntity;

    @Column(name = "mesa_id", nullable = false)
    private Long mesaId;

    @ManyToOne
    @JoinColumn(name = "cliente_id", insertable = false, updatable = false)
    private ClienteEntity clienteEntity;

    @Column(name = "cliente_id", nullable = false)
    private Long clienteId;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataParaReserva;

    // posteriormente pode ser necessário colocar o id dos restaurantes também, apenas da mesa já conter isso

    @Enumerated(EnumType.STRING)
    private StatusReserva statusReserva;

    private int quantidadePessoas;

    private String nomeParaReserva;

    private String observacoes;
    
    @CreationTimestamp
    private LocalDateTime criadoEm;
}
