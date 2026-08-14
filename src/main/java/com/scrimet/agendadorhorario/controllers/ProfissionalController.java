package com.scrimet.agendadorhorario.controllers;

import com.scrimet.agendadorhorario.dtos.CadastroProfissionalDTO;
import com.scrimet.agendadorhorario.dtos.ListarProfissionalDTO;
import com.scrimet.agendadorhorario.services.ProfissionalService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/profissional")
public class ProfissionalController {

    private final ProfissionalService profissionalService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<ListarProfissionalDTO> findById(@PathVariable Long id) {
        ListarProfissionalDTO dto = profissionalService.findById(id);
        return ResponseEntity.ok(dto);
    }
    @GetMapping
    public ResponseEntity<List<ListarProfissionalDTO>> findAll() {
        List<ListarProfissionalDTO> list = profissionalService.findAll();
        return ResponseEntity.ok(list);
    }

    @Transactional(readOnly = true)
    @GetMapping(value = "/buscar/{nome}")
    public List<ListarProfissionalDTO> searchByNome(@PathVariable String nome) {
        return profissionalService.findByNome(nome);
    }
    @Transactional(readOnly = true)
    @GetMapping(value = "/area/{areaProfissional}")
    public List<ListarProfissionalDTO> findByAreaProfissional(@PathVariable String areaProfissional) {
        return profissionalService.findByAreaProfissional(areaProfissional);
    }

    @PostMapping
    public ResponseEntity<ListarProfissionalDTO> save(@RequestBody @Valid CadastroProfissionalDTO dto){
        ListarProfissionalDTO novoProfissional = profissionalService.CadastrarProfissional(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(novoProfissional);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ListarProfissionalDTO> alterarProfissional(@PathVariable Long id, @RequestBody @Valid CadastroProfissionalDTO dto){

        ListarProfissionalDTO agendamentoAtualizado = profissionalService.atualizarProfissional(id,dto);
        return ResponseEntity.ok(agendamentoAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        profissionalService.removerProfissional(id);
        return ResponseEntity.noContent().build();
    }
}
