package com.scrimet.agendadorhorario.services;

import com.scrimet.agendadorhorario.dtos.CadastroClienteDTO;
import com.scrimet.agendadorhorario.dtos.ConsultarClientesDTO;
import com.scrimet.agendadorhorario.infrainstructure.entities.Cliente;
import com.scrimet.agendadorhorario.infrainstructure.repositories.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClienteService {
    private final ClienteRepository clienteRepository;

    public Cliente buscarClientePorId(Long id){
        return clienteRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Cliente não encontrado"));
    }
    public ConsultarClientesDTO findById(Long id){
        return new ConsultarClientesDTO(buscarClientePorId(id));
    }

    public ConsultarClientesDTO cadastrarCliente( CadastroClienteDTO cadastroClienteDTO){
        if (clienteRepository.existsByEmail(cadastroClienteDTO.email())){
            throw new RuntimeException("Cliente já cadastrado");
        }
        Cliente cliente = new Cliente();
        cliente.setNome(cadastroClienteDTO.nome());
        cliente.setEmail(cadastroClienteDTO.email());
        cliente.setTelefone(cadastroClienteDTO.telefone());
        cliente = clienteRepository.save(cliente);
        return new ConsultarClientesDTO(cliente);
    }
    @Transactional(readOnly = true)
    public List<ConsultarClientesDTO> findAll(){
        List<Cliente> clientes = clienteRepository.findAll();
        return clientes.stream()
                .map(ConsultarClientesDTO::new)
                .toList();
    }
    @Transactional
    public void removerCliente(Long id){
        Cliente cliente = buscarClientePorId(id);
        clienteRepository.delete(cliente);
    }
    @Transactional(readOnly = true)
    public List<ConsultarClientesDTO> findByNome(String nome){
        List<Cliente> cliente = clienteRepository.findByNomeContainingIgnoreCase(nome);
        return cliente.stream()
                .map(ConsultarClientesDTO::new)
                .toList();
    }
    @Transactional
    public ConsultarClientesDTO editarCliente(Long id, CadastroClienteDTO cadastroClienteDTO){
        Cliente cliente = buscarClientePorId(id);
        cliente.setNome(cadastroClienteDTO.nome());
        cliente.setEmail(cadastroClienteDTO.email());
        cliente.setTelefone(cadastroClienteDTO.telefone());
        cliente = clienteRepository.save(cliente);
        return new ConsultarClientesDTO(cliente);
    }

}
