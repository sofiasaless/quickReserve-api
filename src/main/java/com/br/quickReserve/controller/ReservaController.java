package com.br.quickReserve.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.br.quickReserve.model.ReservaEntity;
import com.br.quickReserve.service.ReservaService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/reservas")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ReservaController {
    
    private final ReservaService reservaService;

    // trata-se de uma busca a partir do "id da mesa" e da "data para reserva"
    // @PostMapping("/encontrar")
    // public ResponseEntity<ReservaEntity> encontrarReservas(@RequestBody BuscarReversaRequestDTO buscarReversaRequestDTO) {
    //     return ResponseEntity.ok().body(this.reservaService.encontrarReservaPorMesaEData(buscarReversaRequestDTO));
    // }

    // encontrando a reserva de uma mesa por data e idMesa, a função acima fica obsoleta
    // http://localhost:8080/reservas/encontrar/1?dataParaReserva=2025-05-03
    @GetMapping("/encontrar/{mesaId}")
    public ResponseEntity<ReservaEntity> encontrarReservasDaMesaPorPath(@PathVariable Long mesaId, @RequestParam String dataParaReserva) {
        return ResponseEntity.ok().body(this.reservaService.encontrarReservaPorMesaEData(mesaId, dataParaReserva));
    }

    @GetMapping("/listar/mesa/{id}")
    public ResponseEntity<List<ReservaEntity>> encontrarReservasDaMesa(@PathVariable Long id, @RequestParam String status) {
        return ResponseEntity.ok().body(this.reservaService.encontrarReservaPorMesa(id, status.toUpperCase()));
    }

    @GetMapping("/")
    public ResponseEntity<List<ReservaEntity>> listarTodas() {
        return ResponseEntity.ok().body(this.reservaService.listarTodasReservas());
    }


}
