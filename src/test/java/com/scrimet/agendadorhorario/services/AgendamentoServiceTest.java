package com.scrimet.agendadorhorario.services;

import com.scrimet.agendadorhorario.dtos.ConsultarAgendamentosDTO;
import com.scrimet.agendadorhorario.infrainstructure.entities.Agendamento;
import com.scrimet.agendadorhorario.infrainstructure.entities.Cliente;
import com.scrimet.agendadorhorario.infrainstructure.entities.Profissional;
import com.scrimet.agendadorhorario.infrainstructure.repositories.AgendamentoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AgendamentoServiceTest {

    @Mock
    private AgendamentoRepository agendamentoRepository;

    @InjectMocks
    private AgendamentoService agendamentoService;

    @Test
    @DisplayName("Deve retornar uma lista de agendamentos DTO quando encontrar pelo ID do profissional")
    void deveRetornarAgendamentosPorProfissionalComSucesso() {
        // Cenário (Arrange)
        Long profissionalId = 1L;

        Profissional profissional = new Profissional();
        profissional.setId(profissionalId);
        profissional.setNome("Carlos Barbeiro");
        profissional.setAreaProfissional("Barbearia");

        Cliente cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNome("João Cliente");

        Agendamento agendamento = new Agendamento();
        agendamento.setId(10L);
        agendamento.setServico("Corte de Cabelo");
        agendamento.setDataHoraAgendamento(LocalDateTime.now().plusDays(1));
        agendamento.setDataInsercao(LocalDateTime.now());
        agendamento.setProfissional(profissional);
        agendamento.setCliente(cliente);

        // Dizemos para o Mockito: "quando chamarem o repository, retorne esta lista simulada"
        when(agendamentoRepository.findByProfissionalId(profissionalId))
                .thenReturn(List.of(agendamento));

        // Ação (Act)
        List<ConsultarAgendamentosDTO> resultado = agendamentoService.findByProfissional(profissionalId);

        // Verificação (Assert)
        assertNotNull(resultado);
        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
        assertEquals("Corte de Cabelo", resultado.get(0).servico());
        assertEquals("Carlos Barbeiro", resultado.get(0).profissionalNome());
        assertEquals("João Cliente", resultado.get(0).clienteNome());

        // Garante que o método do repositório foi chamado de fato
        verify(agendamentoRepository, times(1)).findByProfissionalId(profissionalId);
    }

    @Test
    @DisplayName("Deve lançar exceção quando não encontrar agendamentos para o profissional")
    void deveLancarExcecaoQuandoNaoEncontrarAgendamentosDoProfissional() {
        // Cenário
        Long profissionalId = 99L;
        when(agendamentoRepository.findByProfissionalId(profissionalId))
                .thenReturn(List.of()); // Retorna lista vazia

        // Ação & Verificação
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            agendamentoService.findByProfissional(profissionalId);
        });

        assertEquals("Nenhum agendamento encontrado", exception.getMessage());
        verify(agendamentoRepository, times(1)).findByProfissionalId(profissionalId);
    }
}