package com.br.quickReserve.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.br.quickReserve.dto.request.BuscarReversaRequestDTO;
import com.br.quickReserve.model.ReservaEntity;
import com.br.quickReserve.service.ReservaService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/reservas")
@RequiredArgsConstructor
public class ReservaController {
    
    private final ReservaService reservaService;

    // trata-se de uma busca a partir do "id da mesa" e da "data para reserva"
    @PostMapping("/encontrar")
    public ResponseEntity<ReservaEntity> encontrarReservas(@RequestBody BuscarReversaRequestDTO buscarReversaRequestDTO) {
        return ResponseEntity.ok().body(this.reservaService.encontrarReservaPorMesaEData(buscarReversaRequestDTO));
    }

    @GetMapping("/")
    public ResponseEntity<List<ReservaEntity>> listarTodas() {
        return ResponseEntity.ok().body(this.reservaService.listarTodasReservas());
    }


}
