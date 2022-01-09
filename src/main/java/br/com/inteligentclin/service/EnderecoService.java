package com.clinicasorridente.pifinalbackend.service;

import com.clinicasorridente.pifinalbackend.entity.Endereco;
import com.clinicasorridente.pifinalbackend.repository.IEnderecoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EnderecoService {

    @Autowired
    private IEnderecoRepository enderecoRepository;


    public Endereco salvar(Endereco endereco) {
        return enderecoRepository.save(endereco);
    }

    public Optional<Endereco> buscarPorId(Long id) {
        return enderecoRepository.findById(id);
    }

    public List<Endereco> buscarTodos() {
        return enderecoRepository.findAll();
    }

    public void excluirPorId(Long id) {
        try {
            enderecoRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("Não é possível excluir um endereço que está vinculado a um " +
                    "paciente.");
        }
    }
}
