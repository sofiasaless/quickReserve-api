package com.br.quickReserve.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.br.quickReserve.dto.request.RestauranteRequestDTO;
import com.br.quickReserve.dto.request.RestauranteUpdateRequestDTO;
import com.br.quickReserve.exception.dto.BadRequestDTO;
import com.br.quickReserve.model.RestauranteEntity;
import com.br.quickReserve.service.RestauranteService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/restaurante")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
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

    @GetMapping("/{id}")
    public ResponseEntity<Object> visualizarRestaurante(@PathVariable Long id) {
        try {
            return ResponseEntity.ok().body(this.restauranteService.visualizarPerfilPorId(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
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

    @PreAuthorize("hasRole('RESTAURANTE')")
    @PutMapping("/atualizar")
    public ResponseEntity<Object> atualizarPerfil(@RequestBody RestauranteUpdateRequestDTO restauranteUpdateRequestDTO, HttpServletRequest request) {
        try {
            var id = request.getAttribute("restaurante_id");
            return new ResponseEntity<>(this.restauranteService.atualizarPerfilRestaurante(Long.valueOf(id.toString()), restauranteUpdateRequestDTO), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new BadRequestDTO(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasRole('RESTAURANTE')")
    @DeleteMapping("/deletar")
    public ResponseEntity<Object> deletarRestaurante(HttpServletRequest request) {
        try {
            var id = request.getAttribute("restaurante_id").toString();
            this.restauranteService.deletarPerfilRestaurante(Long.valueOf(id));
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(new BadRequestDTO(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

}
