package com.br.quickReserve.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.br.quickReserve.model.MesaEntity;

public interface MesaRepository extends JpaRepository<MesaEntity, Long> {
    
}
