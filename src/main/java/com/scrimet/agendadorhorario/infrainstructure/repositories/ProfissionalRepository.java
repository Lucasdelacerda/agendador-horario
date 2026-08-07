package com.scrimet.agendadorhorario.infrainstructure.repositories;

import com.scrimet.agendadorhorario.infrainstructure.entities.Profissional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface ProfissionalRepository extends JpaRepository<Profissional, Long> {


    @Query(nativeQuery = true, value = "SELECT * FROM profissionais_tb WHERE LOWER(nome) LIKE LOWER(CONCAT('%', :nome, '%'))")
    List<Profissional> searchByNome(String nome);


    List<Profissional> findByAreaProfissionalContainingIgnoreCase(String areaProfissional);


    boolean existsByTelefoneOrEmail(String telefone, String email);
}

