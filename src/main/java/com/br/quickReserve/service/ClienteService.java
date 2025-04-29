package com.br.quickReserve.service;

import java.util.List;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.br.quickReserve.dto.request.ClienteRequestDTO;
import com.br.quickReserve.dto.request.ClienteUpdateRequestDTO;
import com.br.quickReserve.dto.response.PerfilClienteReponseDTO;
import com.br.quickReserve.exception.ClienteJaCadastradoException;
import com.br.quickReserve.model.ClienteEntity;
import com.br.quickReserve.repository.ClienteRepository;
import com.br.quickReserve.repository.ReservaRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClienteService {
    
    private final ClienteRepository clienteRepository;

    private final ReservaRepository reservaRepository;

    private final PasswordEncoder passwordEncoder;

    public ClienteEntity salvarCliente(ClienteRequestDTO clienteRequestDTO) {
        this.clienteRepository.findByCpfOrEmail(clienteRequestDTO.getCpf(), clienteRequestDTO.getEmail()).ifPresent((cli) -> {
            throw new ClienteJaCadastradoException();
        });
        
        var entidadeCliente = ClienteEntity.builder()
            .nome(clienteRequestDTO.getNome())
            .cpf(clienteRequestDTO.getCpf())
            .email(clienteRequestDTO.getEmail())
            .senha(passwordEncoder.encode(clienteRequestDTO.getSenha()))
            .dataAniversario(clienteRequestDTO.getDataAniversario())
        .build();
        return this.clienteRepository.save(entidadeCliente);
    }

    public List<ClienteEntity> listarTodosClientes() {
        return this.clienteRepository.findAll();
    }

    public PerfilClienteReponseDTO visualizarPerfilPorId(Long id) {
        var cliente = this.clienteRepository.findById(id).orElseThrow(() -> {
            throw new UsernameNotFoundException("Cliente não encontrado!");
        });

        return new PerfilClienteReponseDTO(
            cliente.getId(),
            cliente.getNome(),
            cliente.getCpf(),
            cliente.getEmail(),
            cliente.getDataAniversario(),
            cliente.getCriadoEm()
        );

    }

    public PerfilClienteReponseDTO atualizarPerfilCliente(Long id, ClienteUpdateRequestDTO clienteUpdateRequestDTO) {
        var clienteDesatualizado = this.clienteRepository.findById(id).get();

        clienteDesatualizado.setNome(clienteUpdateRequestDTO.getNome());
        clienteDesatualizado.setDataAniversario(clienteUpdateRequestDTO.getDataAniversario());
        clienteDesatualizado.setSenha(passwordEncoder.encode(clienteUpdateRequestDTO.getSenha()));

        var clienteAtualizado = this.clienteRepository.save(clienteDesatualizado);

        return new PerfilClienteReponseDTO(
            clienteAtualizado.getId(),
            clienteAtualizado.getNome(),
            clienteAtualizado.getCpf(),
            clienteAtualizado.getEmail(),
            clienteAtualizado.getDataAniversario(),
            clienteAtualizado.getCriadoEm()
        );
    }

    @Transactional // aqui é necessário porque se trata de uma transação, já que está apagando em várias tabelas e pode ocorrer row back
    public void deletarPerfilCliente(Long id) {
        // necessario primeiro apagar as reservas no id do cliente
        this.reservaRepository.deleteAllByClienteId(id);

        // finalmente deletando o cliente pelo id
        this.clienteRepository.deleteById(id);
    }

}
