package com.scrimet.agendadorhorario.controller;

import com.scrimet.agendadorhorario.dtos.CadastroClienteDTO;
import com.scrimet.agendadorhorario.dtos.ConsultarClientesDTO;
import com.scrimet.agendadorhorario.services.ClienteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(value = "/clientes")
@RequiredArgsConstructor

public class ClienteController {

    private final ClienteService clienteService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<ConsultarClientesDTO> findById(@PathVariable Long id){
        ConsultarClientesDTO clienteDTO = clienteService.findById(id);
        return ResponseEntity.ok(clienteDTO);
    }
    @Transactional(readOnly = true)
    @GetMapping(value = "/nome")
    public List<ConsultarClientesDTO> findByNome(@RequestParam String nome){
        return clienteService.findByNome(nome);
    }
    @GetMapping
    public ResponseEntity<List<ConsultarClientesDTO>> findAll(){
        List<ConsultarClientesDTO> clienteDTOs = clienteService.findAll();
        return ResponseEntity.ok(clienteDTOs);
    }
    @PostMapping
    public ResponseEntity<ConsultarClientesDTO> save( @RequestBody @Valid
                                                     CadastroClienteDTO dto){
        ConsultarClientesDTO novoCliente = clienteService.cadastrarCliente(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoCliente);
    }
    @PutMapping("/{id}")
    public ResponseEntity<ConsultarClientesDTO> update(@PathVariable  Long id, @RequestBody @Valid
    CadastroClienteDTO dto){
        ConsultarClientesDTO clienteAtualizado = clienteService.editarCliente(id,dto);
        return ResponseEntity.ok(clienteAtualizado);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id){
        clienteService.removerCliente(id);
        return ResponseEntity.noContent().build();
    }
}
