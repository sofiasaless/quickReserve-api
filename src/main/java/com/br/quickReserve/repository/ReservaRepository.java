package com.br.quickReserve.repository;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.br.quickReserve.model.ReservaEntity;
import com.br.quickReserve.model.enums.StatusReserva;

import jakarta.transaction.Transactional;

import java.util.List;

public interface ReservaRepository extends JpaRepository<ReservaEntity, Long> {
    // para procurar reservas por mesa_id e data, verificando se estão disponíveis
    Optional<ReservaEntity> findByDataParaReservaAndMesaId(LocalDate dataParaReserva, Long mesaId);

    List<ReservaEntity> findByMesaIdAndStatusReserva(Long mesaId, StatusReserva statusReserva);

    // query responsável por filtrar reservas de um restaurante, usando sql nativo por enquanto
    @Query(
        value = "SELECT rs.* FROM reservas rs " +
            "JOIN mesas me ON rs.mesa_id = me.id " +
            "JOIN restaurantes rt ON me.restaurante_id = rt.id " +
            "WHERE rt.id = :restauranteId AND rs.status_reserva = :statusReserva", 
        nativeQuery = true
    )
    List<ReservaEntity> findByRestauranteId(@Param("restauranteId") Long restauranteId, @Param("statusReserva") String statusReserva);

    void deleteAllByClienteId(Long clienteId);

    // query responsável por deletar as reservas a partir dos id dos restaurantes das mesas
    @Modifying
    @Transactional
    @Query(
        value = "DELETE FROM reservas rs " +
               "USING mesas me, restaurantes rt " +
               "WHERE rs.mesa_id = me.id " +
               "AND me.restaurante_id = rt.id " +
               "AND rt.id = :restauranteId", 
        nativeQuery = true
    )
    void deleteAllByRestauranteId(@Param("restauranteId") Long restauranteId);



}
