package com.br.quickReserve.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.br.quickReserve.dto.request.BuscarReversaRequestDTO;
import com.br.quickReserve.dto.request.ReservaRequestDTO;
import com.br.quickReserve.model.ReservaEntity;
import com.br.quickReserve.service.ReservaService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/reserva")
@RequiredArgsConstructor
public class ReservaController {
    
    private final ReservaService reservaService;

    @PostMapping("/nova-reserva")
    public ResponseEntity<Object> efetuarReserva(@RequestBody ReservaRequestDTO reservaRequestDTO) {
        return new ResponseEntity<>(this.reservaService.salvarReserva(reservaRequestDTO), HttpStatus.CREATED);
    }

    @PostMapping("/encontrar")
    public ResponseEntity<ReservaEntity> encontrarReservas(@RequestBody BuscarReversaRequestDTO buscarReversaRequestDTO) {
        return ResponseEntity.ok().body(this.reservaService.encontrarReserva(buscarReversaRequestDTO));
    }


}
