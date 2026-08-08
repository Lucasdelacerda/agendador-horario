package com.scrimet.agendadorhorario.infrainstructure.repositories;

import com.scrimet.agendadorhorario.infrainstructure.entities.Agendamento;
import com.scrimet.agendadorhorario.infrainstructure.entities.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import java.util.List;


public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    List<Cliente> findByNomeContainingIgnoreCase(String nome);
    boolean existsByEmail(String email);
}
