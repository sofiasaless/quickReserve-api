package com.br.quickReserve.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.br.quickReserve.dto.request.ReservaUpdateRequestDTO;
import com.br.quickReserve.exception.dto.BadRequestDTO;
import com.br.quickReserve.service.ReservaService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/reservas/restaurante")
@RequiredArgsConstructor
public class ReservaRestauranteController {
    
    private final ReservaService reservaService;

    @PreAuthorize("hasRole('RESTAURANTE')")
    @PostMapping("/atualizar")
    public ResponseEntity<Object> cadastrarCliente(@RequestBody ReservaUpdateRequestDTO reservaUpdateRequestDTO) {
        try {
            return new ResponseEntity<>(this.reservaService.atualizarReserva(reservaUpdateRequestDTO), HttpStatus.OK);            
        } catch (Exception e) {
            return new ResponseEntity<>(new BadRequestDTO(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
}
