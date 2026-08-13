package com.scrimet.agendadorhorario.infrainstructure.repositories;


import com.scrimet.agendadorhorario.infrainstructure.entities.Profissional;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;


public interface ProfissionalRepository extends JpaRepository<Profissional, Long> {


    List<Profissional> findByNomeContainingIgnoreCase(String nome);


    List<Profissional> findByAreaProfissionalContainingIgnoreCase(String areaProfissional);


    boolean existsByTelefoneOrEmail(String telefone, String email);
}

