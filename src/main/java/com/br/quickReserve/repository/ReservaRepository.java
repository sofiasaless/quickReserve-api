package com.br.quickReserve.repository;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.br.quickReserve.model.ReservaEntity;
import java.util.List;


public interface ReservaRepository extends JpaRepository<ReservaEntity, Long> {
    // para procurar reservas por mesa_id e data, verificando se estão disponíveis
    Optional<ReservaEntity> findByDataParaReservaAndMesaId(LocalDate dataParaReserva, Long mesaId);

}
