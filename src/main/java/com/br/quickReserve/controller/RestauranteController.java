package com.br.quickReserve.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.br.quickReserve.model.RestauranteEntity;
import com.br.quickReserve.service.RestauranteService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/restaurante")
@RequiredArgsConstructor
public class RestauranteController {
    
    private final RestauranteService restauranteService;

    @PostMapping("/cadastrar")
    public ResponseEntity<Object> cadastrarRestaurante(@RequestBody RestauranteEntity restauranteEntity) {
        // return ResponseEntity.ok().body(this.restauranteService.salvarRestaurante(restauranteEntity));
        return new ResponseEntity<>(this.restauranteService.salvarRestaurante(restauranteEntity), HttpStatus.CREATED);
    }

    @GetMapping("/")
    public ResponseEntity<List<RestauranteEntity>> listarTodos() {
        return ResponseEntity.ok().body(this.restauranteService.listarTodosRestaurantes());
    }

}
