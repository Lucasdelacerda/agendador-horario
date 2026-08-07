package com.scrimet.agendadorhorario.infrainstructure.repositories;

import com.scrimet.agendadorhorario.infrainstructure.entities.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import java.util.List;


public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    @Query(nativeQuery = true, value = "SELECT * FROM cliente_tb WHERE LOWER(nome) LIKE LOWER(CONCAT('%', :nome, '%'))")
    List<Cliente> searchByNome(String nome);
    boolean existsByEmail(String email);
}
