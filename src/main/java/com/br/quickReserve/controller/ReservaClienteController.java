package com.br.quickReserve.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.br.quickReserve.dto.request.ReservaRequestDTO;
import com.br.quickReserve.dto.request.ReservaUpdateRequestDTO;
import com.br.quickReserve.exception.dto.BadRequestDTO;
import com.br.quickReserve.model.ReservaEntity;
import com.br.quickReserve.model.enums.StatusReserva;
import com.br.quickReserve.service.ReservaService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/reservas/cliente")
@RequiredArgsConstructor
@PreAuthorize("hasRole('CLIENTE')")
public class ReservaClienteController {

    private final ReservaService reservaService;

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

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<Object> atualizarReserva(@PathVariable Long id, @RequestBody ReservaUpdateRequestDTO reservaUpdateRequestDTO, HttpServletRequest request) {
        try {
            // mesmo que o cliente mande o status da reserva como 'CONFIRMADA' ou 'CANCELADA', o status vai chegar como pendente
            reservaUpdateRequestDTO.setStatusReserva(StatusReserva.PENDENTE);
            var clienteId = request.getAttribute("cliente_id").toString();

            return new ResponseEntity<>(this.reservaService.atualizarReservaViaCliente(id, reservaUpdateRequestDTO, Long.valueOf(clienteId)), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new BadRequestDTO(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/listarTodas")
    public ResponseEntity<List<ReservaEntity>> listarReservasCiente(HttpServletRequest request) {
        var clienteId = request.getAttribute("cliente_id").toString();
        return ResponseEntity.ok().body(this.reservaService.listarTodasReservasPorCliente(Long.valueOf(clienteId)));
    }


    
}
