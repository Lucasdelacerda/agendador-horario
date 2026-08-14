package com.scrimet.agendadorhorario.dtos;

import com.scrimet.agendadorhorario.infrainstructure.entities.Agendamento;

import java.time.LocalDateTime;

public record ConsultarAgendamentosDTO(
        Long id,
        String servico,
        LocalDateTime dataHoraAgendamento,
        String profissionalNome,
        String profissionalAreaProfissional,
        String clienteNome,
        LocalDateTime dataInsercao

) {
    public ConsultarAgendamentosDTO (Agendamento agendamento){
        this(
                agendamento.getId(),
                agendamento.getServico(),
                agendamento.getDataHoraAgendamento(),
                agendamento.getProfissional().getNome(),
                agendamento.getProfissional().getAreaProfissional(),
                agendamento.getCliente().getNome(),
                agendamento.getDataInsercao()
        );
    }
}
