package com.scrimet.agendadorhorario.infrainstructure.repositories;


import com.scrimet.agendadorhorario.dtos.ConsultarAgendamentosDTO;
import com.scrimet.agendadorhorario.dtos.MarcarAgendamentoDTO;
import com.scrimet.agendadorhorario.infrainstructure.entities.Agendamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {

    List<Agendamento> findByProfissionalId(Long profissionalId);

    List<Agendamento> findByClienteId(Long clienteId);
    List<Agendamento> findByCliente_NomeContainingIgnoreCase(String nomeCliente);

    List<Agendamento> findByProfissional_NomeContainingIgnoreCase(String nomeProfissional);
    boolean existsByOverlapping(@Param("inicioNovo") LocalDateTime inicioNovo, @Param("fimNovo") LocalDateTime fimNovo);

    List<ConsultarAgendamentosDTO> findByDataHoraAgendamentoBetween(LocalDateTime dataHoraInicio, LocalDateTime dataHoraFinal);

}
