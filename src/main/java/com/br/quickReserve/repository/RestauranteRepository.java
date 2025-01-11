package com.br.quickReserve.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.br.quickReserve.model.RestauranteEntity;

public interface RestauranteRepository extends JpaRepository<RestauranteEntity, Long> {
    Optional<RestauranteEntity> findByEmail(String email);
    Optional<RestauranteEntity> findByCnpj(String cnpj);
    Optional<RestauranteEntity> findByCnpjOrEmail(String cnpj, String email);
}
