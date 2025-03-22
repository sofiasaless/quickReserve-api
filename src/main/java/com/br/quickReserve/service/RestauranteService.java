package com.br.quickReserve.service;

import java.util.List;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.br.quickReserve.dto.request.RestauranteRequestDTO;
import com.br.quickReserve.dto.request.RestauranteUpdateRequestDTO;
import com.br.quickReserve.dto.response.PerfilRestauranteReponseDTO;
import com.br.quickReserve.exception.RestauranteJaCadastradoException;
import com.br.quickReserve.model.RestauranteEntity;
import com.br.quickReserve.repository.MesaRepository;
import com.br.quickReserve.repository.ReservaRepository;
import com.br.quickReserve.repository.RestauranteRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RestauranteService {
    
    private final RestauranteRepository restauranteRepository;

    private final ReservaRepository reservaRepository;

    private final MesaRepository mesaRepository;

    private final PasswordEncoder passwordEncoder;

    public RestauranteEntity salvarRestaurante(RestauranteRequestDTO restauranteRequestDTO) {
        this.restauranteRepository.findByCnpjOrEmail(restauranteRequestDTO.getCnpj(), restauranteRequestDTO.getEmail()).ifPresent((user) -> {
            throw new RestauranteJaCadastradoException();
        });
        
        var entidadeRestaurante = RestauranteEntity.builder()
            .nome(restauranteRequestDTO.getNome())
            .cnpj(restauranteRequestDTO.getCnpj())
            .email(restauranteRequestDTO.getEmail())
            .senha(passwordEncoder.encode(restauranteRequestDTO.getSenha()))
        .build();
        return this.restauranteRepository.save(entidadeRestaurante);
    }

    public List<RestauranteEntity> listarTodosRestaurantes() {
        return this.restauranteRepository.findAll();
    }

    public PerfilRestauranteReponseDTO visualizarPerfilPorId(Long id) {
        var restaurante = this.restauranteRepository.findById(id).orElseThrow(() -> {
            throw new UsernameNotFoundException("Restaurante não encontrado!");
        });

        return new PerfilRestauranteReponseDTO(
            restaurante.getId(),
            restaurante.getNome(),
            restaurante.getCnpj(),
            restaurante.getEmail(),
            restaurante.getCriadoEm()
        );

    }

    public PerfilRestauranteReponseDTO atualizarPerfilRestaurante(Long id, RestauranteUpdateRequestDTO restauranteUpdateRequestDTO) {
        var restauranteDesatualizado = this.restauranteRepository.findById(id).get();

        restauranteDesatualizado.setNome(restauranteUpdateRequestDTO.getNome());
        restauranteDesatualizado.setSenha(passwordEncoder.encode(restauranteUpdateRequestDTO.getSenha()));

        var restauranteAtualizado = this.restauranteRepository.save(restauranteDesatualizado);

        return new PerfilRestauranteReponseDTO(
            restauranteAtualizado.getId(),
            restauranteAtualizado.getNome(),
            restauranteAtualizado.getCnpj(),
            restauranteAtualizado.getEmail(),
            restauranteAtualizado.getCriadoEm()
        );
    }

    @Transactional
    public void deletarPerfilRestaurante(Long id) {
        // necessario primeiro apagar todas as reservas do restaurante
        this.reservaRepository.deleteAllByRestauranteId(id);

        // depois necessario apagar todas as mesas do restaurante
        this.mesaRepository.deleteAllByRestauranteId(id);

        // só então apagar o restaurante
        this.restauranteRepository.deleteById(id);
    }

}
