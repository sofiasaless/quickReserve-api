package com.br.quickReserve.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @GetMapping("/encontrar/mesa/{id}")
    public ResponseEntity<List<ReservaEntity>> encontrarReservasDaMesa(@PathVariable Long id, @RequestParam String status) {
        return ResponseEntity.ok().body(this.reservaService.encontrarReservaPorMesa(id, status.toUpperCase()));
    }

    @GetMapping("/encontrar/restaurante/{id}")
    // aqui é necessário passar o id e o status na uri
    // exemplo: http://localhost:8080/reservas/encontrar/restaurante/3?status=pendente
    public ResponseEntity<List<ReservaEntity>> encontrarReservasDaRestaurante(@PathVariable Long id, @RequestParam String status) {
        return ResponseEntity.ok().body(this.reservaService.encontrarReservaPorRestaurante(id, status.toUpperCase()));
    }

    @GetMapping("/")
    public ResponseEntity<List<ReservaEntity>> listarTodas() {
        return ResponseEntity.ok().body(this.reservaService.listarTodasReservas());
    }


}
