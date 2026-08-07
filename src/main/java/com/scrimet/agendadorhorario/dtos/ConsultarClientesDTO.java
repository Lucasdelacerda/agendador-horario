package com.scrimet.agendadorhorario.dtos;

import com.scrimet.agendadorhorario.infrainstructure.entities.Cliente;

public record ConsultarClientesDTO(
        Long id,
        String nome,
        String email,
        String telefone
) {
    public ConsultarClientesDTO(Cliente cliente) {
        this(
                cliente.getId(),
                cliente.getNome(),
                cliente.getEmail(),
                cliente.getTelefone()
        );
    }
}
