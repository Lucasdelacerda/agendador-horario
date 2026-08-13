package com.scrimet.agendadorhorario.services;


import com.scrimet.agendadorhorario.dtos.ConsultarAgendamentosDTO;
import com.scrimet.agendadorhorario.dtos.MarcarAgendamentoDTO;
import com.scrimet.agendadorhorario.infrainstructure.entities.Agendamento;
import com.scrimet.agendadorhorario.infrainstructure.entities.Cliente;
import com.scrimet.agendadorhorario.infrainstructure.entities.Profissional;
import com.scrimet.agendadorhorario.infrainstructure.repositories.AgendamentoRepository;
import com.scrimet.agendadorhorario.infrainstructure.repositories.ClienteRepository;
import com.scrimet.agendadorhorario.infrainstructure.repositories.ProfissionalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AgendamentoService {
    private final AgendamentoRepository agendamentoRepository;
    private final ProfissionalRepository profissionalRepository;
    private final ClienteRepository clienteRepository;

    @Transactional(readOnly = true)
    public Agendamento BuscarAgendamentoPorId(Long id){
        return agendamentoRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Agendamento não encontrado"));}
    public ConsultarAgendamentosDTO findById(Long id){return  new ConsultarAgendamentosDTO(BuscarAgendamentoPorId(id));}

    public ConsultarAgendamentosDTO salvarAgendamento (MarcarAgendamentoDTO marcarAgendamentoDTO){
        if(marcarAgendamentoDTO.dataHoraAgendamento().isBefore(LocalDateTime.now())){
            throw new RuntimeException("Horário inválido. Anterior a data atual");
        }
        if(marcarAgendamentoDTO.dataHoraAgendamento() == null){
            throw new RuntimeException("Data de agendamento é obrigatória");
        }
        LocalDateTime dataHoraFimCalculada = marcarAgendamentoDTO.dataHoraAgendamento().plusMinutes(30);
        boolean Conflito = agendamentoRepository.existsByOverlapping(marcarAgendamentoDTO.dataHoraAgendamento(), dataHoraFimCalculada);

        if(Conflito){
            throw new RuntimeException("Horário já preenchido");
        }
        Cliente cliente = clienteRepository.findById(marcarAgendamentoDTO.clienteId())
                .orElseThrow(()-> new RuntimeException("Cliente não encontrado") );
        Profissional profissional = profissionalRepository.findById(marcarAgendamentoDTO.profissionalId())
                .orElseThrow(()-> new RuntimeException("Profissional não encontrado") );

        Agendamento agendamento = new Agendamento();
        agendamento.setDataHoraAgendamento(marcarAgendamentoDTO.dataHoraAgendamento());
        agendamento.setServico(marcarAgendamentoDTO.servico());
        agendamento.setDataFinalizacao(dataHoraFimCalculada);
        agendamento.setCliente(cliente);
        agendamento.setProfissional(profissional);

        Agendamento agendamentoSalvo = agendamentoRepository.save(agendamento);
        return new ConsultarAgendamentosDTO(agendamentoSalvo);
    }
    public List<ConsultarAgendamentosDTO> buscarAgendamentosDia(LocalDateTime data){
        LocalDateTime primeiraHoraDia = data.toLocalDate().atStartOfDay();
        LocalDateTime horaFinalDia = data.toLocalDate().plusDays(1).atStartOfDay();

        List<ConsultarAgendamentosDTO> today = agendamentoRepository.findByDataHoraAgendamentoBetween(primeiraHoraDia, horaFinalDia);

        if(today.isEmpty()){
            throw new RuntimeException("Não há agendamentos para hoje");
        }
        return today;
    }
    @Transactional
    public ConsultarAgendamentosDTO alterarAgendamentoPorId (Long id, ConsultarAgendamentosDTO marcarAgendamentoDTO){

        Agendamento agendamento = BuscarAgendamentoPorId(id);

        agendamento.setDataHoraAgendamento(marcarAgendamentoDTO.dataHoraAgendamento());
        return new ConsultarAgendamentosDTO(agendamento);
    }
    public void deletarAgendamento(Long id){
        Agendamento agendamento = BuscarAgendamentoPorId(id);
        agendamentoRepository.deleteById(id);
    }
    public List<Agendamento> findAll(){
        List<Agendamento> todos = agendamentoRepository.findAll();

        if(todos.isEmpty()){
            throw new RuntimeException("Nenhum agendamento encontrado");

        }
        return todos;
    }
    public List<ConsultarAgendamentosDTO> findByProfissional(Long profissionalId){

        List<Agendamento> agendamentos = agendamentoRepository.findByProfissionalId(profissionalId);
        if(agendamentos.isEmpty()){
            throw new RuntimeException("Nenhum agendamento encontrado");

        }
        return agendamentos.stream()
                .map(ConsultarAgendamentosDTO::new)
                .toList();
    }

}

