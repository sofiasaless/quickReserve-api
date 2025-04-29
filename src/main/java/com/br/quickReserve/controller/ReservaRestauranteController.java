package com.br.quickReserve.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.br.quickReserve.dto.request.ReservaUpdateRequestDTO;
import com.br.quickReserve.exception.dto.BadRequestDTO;
import com.br.quickReserve.model.ReservaEntity;
import com.br.quickReserve.service.ReservaService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/reservas/restaurante")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ReservaRestauranteController {
    
    private final ReservaService reservaService;

    @PreAuthorize("hasRole('RESTAURANTE')")
    @PutMapping("/atualizar")
    public ResponseEntity<Object> cadastrarCliente(@RequestBody ReservaUpdateRequestDTO reservaUpdateRequestDTO) {
        // posteriormente é necessário validar se a reserva a ser atualizada pertence ao restaurante que está a atualizando
        try {
            return new ResponseEntity<>(this.reservaService.atualizarReserva(reservaUpdateRequestDTO), HttpStatus.OK);            
        } catch (Exception e) {
            return new ResponseEntity<>(new BadRequestDTO(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasRole('RESTAURANTE')")
    @GetMapping("/listar")
    // aqui é necessário passar o id e o status na uri
    // exemplo: http://localhost:8080/reservas/encontrar/restaurante/3?status=pendente
    public ResponseEntity<List<ReservaEntity>> encontrarReservasDaRestaurante(@RequestParam String status, HttpServletRequest request) {
        var restauranteId = request.getAttribute("restaurante_id").toString();
        return ResponseEntity.ok().body(this.reservaService.encontrarReservaPorRestaurante(Long.valueOf(restauranteId), status.toUpperCase()));        
    }

    @PreAuthorize("hasRole('RESTAURANTE')")
    @GetMapping("/listarTodas")
    // aqui é necessário passar o id e o status na uri
    // exemplo: http://localhost:8080/reservas/encontrar/restaurante/3?status=pendente
    public ResponseEntity<List<ReservaEntity>> encontrarReservasDaRestaurante(HttpServletRequest request) {
        var restauranteId = request.getAttribute("restaurante_id").toString();
        return ResponseEntity.ok().body(this.reservaService.listarTodasReservasPorRestaurante(Long.valueOf(restauranteId)));
    }


}
