package com.br.quickReserve.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.br.quickReserve.dto.request.MesaRequestDTO;
import com.br.quickReserve.model.MesaEntity;
import com.br.quickReserve.service.MesaService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/restaurante/mesas")
@RequiredArgsConstructor
public class MesaController {
    
    private final MesaService mesaService;

    @PreAuthorize("hasRole('RESTAURANTE')")
    @PostMapping("/nova-mesa")
    public ResponseEntity<Object> cadastrarMesa(@RequestBody MesaRequestDTO mesaRequestDTO) {
        return new ResponseEntity<>(this.mesaService.salvarMesa(mesaRequestDTO), HttpStatus.CREATED);
    }

    // mapeando as mesas do restaurante
    @GetMapping("/{id}")
    public ResponseEntity<List<MesaEntity>> listarMesas(@PathVariable Long id) {
        return ResponseEntity.ok().body(this.mesaService.listarMesasPorEstabelecimento(id));
    }

}
