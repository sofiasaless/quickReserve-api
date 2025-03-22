package com.br.quickReserve.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.br.quickReserve.model.MesaEntity;
import java.util.List;


public interface MesaRepository extends JpaRepository<MesaEntity, Long> {
    List<MesaEntity> findByRestauranteId(Long restauranteId);  
    
    void deleteAllByRestauranteId(Long restauranteId);
}
