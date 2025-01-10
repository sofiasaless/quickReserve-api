package com.br.quickReserve.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.br.quickReserve.model.MesaEntity;
import com.br.quickReserve.service.MesaService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/mesas")
@RequiredArgsConstructor
public class MesaController {
    
    private final MesaService mesaService;

    @PostMapping("/nova-mesa")
    public ResponseEntity<Object> cadastrarMesa(@RequestBody MesaEntity mesaEntity) {
        return ResponseEntity.ok().body(this.mesaService.salvarMesa(mesaEntity));
    }

    // mapeando as mesas do restaurante
    @GetMapping("/restaurante/{id}")
    public ResponseEntity<List<MesaEntity>> listarMesas(@PathVariable Long id) {
        return ResponseEntity.ok().body(this.mesaService.listarMesasPorEstabelecimento(id));
    }

}
