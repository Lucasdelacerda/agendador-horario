package com.scrimet.agendadorhorario.dtos;


import java.time.LocalDateTime;

public record MarcarAgendamentoDTO(
        LocalDateTime dataHoraAgendamento,
        String servico,
        Long clienteId,
        Long profissionalId
) {}
