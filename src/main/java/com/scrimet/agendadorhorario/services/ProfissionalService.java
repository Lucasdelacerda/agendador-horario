package com.scrimet.agendadorhorario.services;

import com.scrimet.agendadorhorario.dtos.CadastroProfissionalDTO;
import com.scrimet.agendadorhorario.dtos.ListarProfissionalDTO;
import com.scrimet.agendadorhorario.infrainstructure.entities.Profissional;
import com.scrimet.agendadorhorario.infrainstructure.repositories.ProfissionalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ProfissionalService {

    private final ProfissionalRepository profissionalRepository;

    @Transactional(readOnly = true)
    public Profissional buscarPorId(Long id){
        return profissionalRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Usuário não encontrado"));
    }
    public ListarProfissionalDTO findById(Long id){return new  ListarProfissionalDTO(buscarPorId(id));}

    public ListarProfissionalDTO CadastrarProfissional(CadastroProfissionalDTO cadastroProfissionalDTO){

        if (profissionalRepository.existsByTelefoneOrEmail(cadastroProfissionalDTO.telefone(), cadastroProfissionalDTO.email()) ){
            throw new RuntimeException("Telefone e email já cadastrado");
        }

        Profissional profissional = new Profissional();

        profissional.setNome(cadastroProfissionalDTO.nome());
        profissional.setEmail(cadastroProfissionalDTO.email());
        profissional.setTelefone(cadastroProfissionalDTO.telefone());
        profissional.setEndereco(cadastroProfissionalDTO.endereco());
        profissional.setAreaProfissional(cadastroProfissionalDTO.areaProfissional());

        profissionalRepository.save(profissional);
        return  new  ListarProfissionalDTO(profissional);
    }

    @Transactional
    public ListarProfissionalDTO atualizarProfissional(Long id,CadastroProfissionalDTO cadastroProfissionalDTO){
        Profissional profissional = buscarPorId(id);
        profissional.setNome(cadastroProfissionalDTO.nome());
        profissional.setEmail(cadastroProfissionalDTO.email());
        profissional.setTelefone(cadastroProfissionalDTO.telefone());
        profissional.setEndereco(cadastroProfissionalDTO.endereco());
        profissional.setAreaProfissional(cadastroProfissionalDTO.areaProfissional());

        profissionalRepository.save(profissional);
        return  new  ListarProfissionalDTO(profissional);
    }
    @Transactional
    public void removerProfissional(Long id){
        Profissional profissional = buscarPorId(id);
        profissionalRepository.delete(profissional);
    }
    @Transactional(readOnly = true)
    public List<ListarProfissionalDTO> findByNome(String nome){
        List<Profissional> profissional = profissionalRepository.findByNomeContainingIgnoreCase(nome);
        return profissional.stream()
                .map(ListarProfissionalDTO::new)
                .toList();

    }
    @Transactional(readOnly = true)
    public List<ListarProfissionalDTO> findByAreaProfissional(String areaProfissional){
        List<Profissional> profissional = profissionalRepository.findByAreaProfissionalContainingIgnoreCase(areaProfissional);
        return profissional.stream()
                .map(ListarProfissionalDTO::new)
                .toList();

    }
    @Transactional(readOnly = true)
    public List<ListarProfissionalDTO> findAll(){
        List<Profissional> profissionais = profissionalRepository.findAll();

        return profissionais.stream()
                .map(ListarProfissionalDTO::new)
                .toList();
    }
}