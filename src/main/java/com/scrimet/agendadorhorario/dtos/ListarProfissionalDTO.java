package com.scrimet.agendadorhorario.dtos;

import com.scrimet.agendadorhorario.infrainstructure.entities.Profissional;
import jakarta.validation.constraints.Pattern;

public record ListarProfissionalDTO(
        Long id,
        String nome,
        String endereco,
        String telefone,
        String email,
        String areaProfissional
) {
    public ListarProfissionalDTO (Profissional profissional){
        this(
                profissional.getId(),
                profissional.getNome(),
                profissional.getEndereco(),
                profissional.getTelefone(),
                profissional.getEmail(),
                profissional.getAreaProfissional()
        );
    }
}
