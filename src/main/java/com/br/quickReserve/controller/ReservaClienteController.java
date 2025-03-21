package com.br.quickReserve.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.br.quickReserve.dto.request.ReservaRequestDTO;
import com.br.quickReserve.exception.dto.BadRequestDTO;
import com.br.quickReserve.service.ReservaService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/reservas/cliente")
@RequiredArgsConstructor
public class ReservaClienteController {

    private final ReservaService reservaService;

    @PreAuthorize("hasRole('CLIENTE')")
    @PostMapping("/nova-reserva") // refatorar metodo: o requestbody nao deve pedir o id do cliente, o id do cliente deve ser passado por token
    public ResponseEntity<Object> efetuarReserva(@RequestBody ReservaRequestDTO reservaRequestDTO) {
        try {
            return new ResponseEntity<>(this.reservaService.salvarReserva(reservaRequestDTO), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(new BadRequestDTO(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
    
}
