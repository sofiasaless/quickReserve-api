package com.br.quickReserve.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.br.quickReserve.dto.request.AuthRequestDTO;
import com.br.quickReserve.dto.response.TokenResponseDTO;
import com.br.quickReserve.exception.dto.BadRequestDTO;
import com.br.quickReserve.service.AuthUsuariosService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/entrar")
@RequiredArgsConstructor
public class AuthUsuariosController {
    
    private final AuthUsuariosService authUsuariosService;

    @PostMapping("/cliente")
    public ResponseEntity<Object> entrarComoCliente(@RequestBody AuthRequestDTO authRequestDTO) {
        try {
            return new ResponseEntity<>(new TokenResponseDTO(this.authUsuariosService.entrarComoCliente(authRequestDTO)), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new BadRequestDTO(e.getMessage()), HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/restaurante")
    public ResponseEntity<Object> entrarComoRestaurante(@RequestBody AuthRequestDTO authRequestDTO) {
        try {
            return new ResponseEntity<>(new TokenResponseDTO(this.authUsuariosService.entrarComoRestaurante(authRequestDTO)), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new BadRequestDTO(e.getMessage()), HttpStatus.UNAUTHORIZED);
        }        
    }

}
