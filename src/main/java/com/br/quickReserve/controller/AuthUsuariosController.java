package com.br.quickReserve.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.br.quickReserve.dto.request.AuthRequestDTO;
import com.br.quickReserve.dto.response.TokenResponseDTO;
import com.br.quickReserve.exception.dto.BadRequestDTO;
import com.br.quickReserve.model.enums.TipoUsuario;
import com.br.quickReserve.service.AuthUsuariosService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AuthUsuariosController {
    
    private final AuthUsuariosService authUsuariosService;

    @PostMapping("/entrar/cliente")
    public ResponseEntity<Object> entrarComoCliente(@RequestBody AuthRequestDTO authRequestDTO) {
        try {
            return new ResponseEntity<>(new TokenResponseDTO(this.authUsuariosService.entrarComoCliente(authRequestDTO)), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new BadRequestDTO(e.getMessage()), HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/entrar/restaurante")
    public ResponseEntity<Object> entrarComoRestaurante(@RequestBody AuthRequestDTO authRequestDTO) {
        try {
            return new ResponseEntity<>(new TokenResponseDTO(this.authUsuariosService.entrarComoRestaurante(authRequestDTO)), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new BadRequestDTO(e.getMessage()), HttpStatus.UNAUTHORIZED);
        }        
    }

    @PreAuthorize("hasRole('CLIENTE')")
    @GetMapping("/cliente/status")
    public ResponseEntity<Object> verificarLoginCliente(HttpServletRequest request) {
        try {
            var id = Long.valueOf(request.getAttribute("cliente_id").toString());
            this.authUsuariosService.verificarStatusUsuario(id, TipoUsuario.CLIENTE);
            return ResponseEntity.accepted().build();
        } catch (Exception e) {
            // return new ResponseEntity<>(new BadRequestDTO(e.getMessage()), HttpStatus.UNAUTHORIZED);
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('RESTAURANTE')")
    @GetMapping("/restaurante/status")
    public ResponseEntity<Object> verificarLoginRestaurante(HttpServletRequest request) {
        try {
            var id = Long.valueOf(request.getAttribute("restaurante_id").toString());
            this.authUsuariosService.verificarStatusUsuario(id, TipoUsuario.RESTAURANTE);
            return ResponseEntity.accepted().build();
        } catch (Exception e) {
            return new ResponseEntity<>(new BadRequestDTO(e.getMessage()), HttpStatus.UNAUTHORIZED);
        }
    }

}