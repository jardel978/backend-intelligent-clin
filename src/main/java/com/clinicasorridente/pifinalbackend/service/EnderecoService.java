package com.clinicasorridente.pifinalbackend.service;

import com.clinicasorridente.pifinalbackend.entity.Endereco;
import com.clinicasorridente.pifinalbackend.repository.IEnderecoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EnderecoService implements IGenericService<Endereco, Long> {

    @Autowired
    private IEnderecoRepository enderecoRepository;


    @Override
    public Endereco salvar(Endereco endereco) {
        return enderecoRepository.save(endereco);
    }

    @Override
    public Optional<Endereco> buscarPorId(Long id) {
        return enderecoRepository.findById(id);
    }

    @Override
    public List<Endereco> buscarTodos() {
        return enderecoRepository.findAll();
    }

    @Override
    public void excluirPorId(Long id) {
        enderecoRepository.deleteById(id);
    }
}
