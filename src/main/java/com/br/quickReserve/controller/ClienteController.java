package com.br.quickReserve.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.br.quickReserve.dto.request.ClienteRequestDTO;
import com.br.quickReserve.exception.dto.BadRequestDTO;
import com.br.quickReserve.model.ClienteEntity;
import com.br.quickReserve.service.ClienteService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/cliente")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService clienteService;

    @PostMapping("/cadastrar")
    public ResponseEntity<Object> cadastrarCliente(@RequestBody ClienteRequestDTO clienteRequestDTO) {
        try {
            return new ResponseEntity<>(this.clienteService.salvarCliente(clienteRequestDTO), HttpStatus.CREATED);            
        } catch (Exception e) {
            return new ResponseEntity<>(new BadRequestDTO(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/")
    public ResponseEntity<List<ClienteEntity>> listarTodos() {
        return ResponseEntity.ok().body(this.clienteService.listarTodosClientes());
    }

    @GetMapping("/perfil")
    public ResponseEntity<Object> visualizarPerfil(HttpServletRequest request) {
        var id = request.getAttribute("cliente_id");
    
        try {
            var perfil = this.clienteService.visualizarPerfilPorId(Long.valueOf(id.toString()));
            return ResponseEntity.ok().body(perfil);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    
    }

}
