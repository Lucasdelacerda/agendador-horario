package com.scrimet.agendadorhorario.dtos;

import jakarta.validation.constraints.Pattern;

public record CadastroProfissionalDTO(
        Long id,
        String nome,
        String endereco,
        @Pattern(regexp = "^\\d{11}$", message = "O telefone deve conter exatamente 11 dígitos (com DDD)")
        String telefone,
        @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.com(\\.br)?$", message = "E-mail inválido. Deve conter @ e terminar com .com")
        String email,
        String areaProfissional
) {}
