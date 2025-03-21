package com.br.quickReserve.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.br.quickReserve.dto.request.RestauranteRequestDTO;
import com.br.quickReserve.exception.dto.BadRequestDTO;
import com.br.quickReserve.model.RestauranteEntity;
import com.br.quickReserve.service.RestauranteService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/restaurante")
@RequiredArgsConstructor
public class RestauranteController {
    
    private final RestauranteService restauranteService;

    @PostMapping("/cadastrar")
    public ResponseEntity<Object> cadastrarRestaurante(@RequestBody RestauranteRequestDTO restauranteEntity) {
        try {
            return new ResponseEntity<>(this.restauranteService.salvarRestaurante(restauranteEntity), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(new BadRequestDTO(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/")
    public ResponseEntity<List<RestauranteEntity>> listarTodos() {
        return ResponseEntity.ok().body(this.restauranteService.listarTodosRestaurantes());
    }

    @GetMapping("/perfil")
    public ResponseEntity<Object> visualizarPerfil(HttpServletRequest request) {
        var id = request.getAttribute("restaurante_id");

        try {
            var perfil = this.restauranteService.visualizarPerfilPorId(Long.valueOf(id.toString()));
            return ResponseEntity.ok().body(perfil);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

}
