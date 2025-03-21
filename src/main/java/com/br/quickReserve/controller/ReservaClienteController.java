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

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/reservas/cliente")
@RequiredArgsConstructor
public class ReservaClienteController {

    private final ReservaService reservaService;

    @PreAuthorize("hasRole('CLIENTE')")
    @PostMapping("/nova-reserva")
    public ResponseEntity<Object> efetuarReserva(@RequestBody ReservaRequestDTO reservaRequestDTO, HttpServletRequest request) {
        try {
            // setando o id do cliente a partir do token que ta sendo passando pelo httpservetrequest
            var id = request.getAttribute("cliente_id").toString();
            reservaRequestDTO.setClienteId(Long.parseLong(id));
            return new ResponseEntity<>(this.reservaService.salvarReserva(reservaRequestDTO), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(new BadRequestDTO(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
    
}
