package com.br.quickReserve.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.br.quickReserve.model.RestauranteEntity;

public interface RestauranteRepository extends JpaRepository<RestauranteEntity, Long> {
    Optional<RestauranteEntity> findByEmail(String email);
    Optional<RestauranteEntity> findByCnpj(String cnpj);
    Optional<RestauranteEntity> findByCnpjOrEmail(String cnpj, String email);

    @Query(
        value = "SELECT rt.* FROM mesas me " +
            "JOIN restaurantes rt ON me.restaurante_id = rt.id " +
            "WHERE me.id = :mesaId " +
            "LIMIT 1", 
        nativeQuery = true
    )
    Optional<RestauranteEntity> findByMesaId(@Param("mesaId") Long mesaId);
}
