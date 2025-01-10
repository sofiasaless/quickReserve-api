package com.br.quickReserve.repository;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.br.quickReserve.model.ReservaEntity;

public interface ReservaRepository extends JpaRepository<ReservaEntity, Long> {
    // para procurar reservas e verificar se estão disponíveis
    Optional<ReservaEntity> findByDataParaReservaAndMesaId(LocalDate dataParaReserva, Long mesaId);
}
