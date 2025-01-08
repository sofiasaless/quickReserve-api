package com.br.quickReserve.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.br.quickReserve.model.ClienteEntity;

public interface ClienteRepository extends JpaRepository<ClienteEntity, Long> {
    
}
