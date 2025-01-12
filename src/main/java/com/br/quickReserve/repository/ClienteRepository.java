package com.br.quickReserve.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.br.quickReserve.model.ClienteEntity;
import java.util.Optional;


public interface ClienteRepository extends JpaRepository<ClienteEntity, Long> {
    Optional<ClienteEntity> findByCpfOrEmail(String cpf, String email);
}
