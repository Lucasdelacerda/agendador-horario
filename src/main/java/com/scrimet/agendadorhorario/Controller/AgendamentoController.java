package com.scrimet.agendadorhorario.Controller;

import com.scrimet.agendadorhorario.dtos.ConsultarAgendamentosDTO;
import com.scrimet.agendadorhorario.dtos.MarcarAgendamentoDTO;
import com.scrimet.agendadorhorario.infrainstructure.entities.Agendamento;
import com.scrimet.agendadorhorario.services.AgendamentoService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/agendamentos")
@RequiredArgsConstructor
public class AgendamentoController {

    private final AgendamentoService agendamentoService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<ConsultarAgendamentosDTO> findById(@PathVariable Long id){
        ConsultarAgendamentosDTO agendamento = agendamentoService.findById(id);
        return ResponseEntity.ok(agendamento);
    }

    @GetMapping
    public ResponseEntity<List<Agendamento>> findAll(){
        List<Agendamento> list = agendamentoService.findAll();
        return ResponseEntity.ok(list);
    }

    @GetMapping(value = "/dia")
    public ResponseEntity<List<ConsultarAgendamentosDTO>> findDia(@RequestParam(name="data") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data){
        var agendamentos = agendamentoService.buscarAgendamentosDia(data.atStartOfDay());
        return ResponseEntity.ok(agendamentos);
    }

    @GetMapping(value = "/profissional/{id}")
    public ResponseEntity<List<ConsultarAgendamentosDTO>> findByProfissional(@PathVariable Long id){
        List<ConsultarAgendamentosDTO> list = agendamentoService.findByProfissional(id);
        return ResponseEntity.ok(list);
    }
    @PostMapping
    public ResponseEntity<ConsultarAgendamentosDTO> salvarAgendamento(@RequestBody MarcarAgendamentoDTO dto){

        ConsultarAgendamentosDTO agendamento = agendamentoService.salvarAgendamento(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(agendamento);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAgendamento(@PathVariable Long id){
        agendamentoService.deletarAgendamento(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ConsultarAgendamentosDTO> alterarAgendamento(@PathVariable Long id, @RequestBody ConsultarAgendamentosDTO dto){
        ConsultarAgendamentosDTO agendamentoAtualizado = agendamentoService.alterarAgendamentoPorId(id,dto);
        return ResponseEntity.ok(agendamentoAtualizado);
    }
}
