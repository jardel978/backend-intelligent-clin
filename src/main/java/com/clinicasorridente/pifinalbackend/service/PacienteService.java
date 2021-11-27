package com.clinicasorridente.pifinalbackend.service;

import com.clinicasorridente.pifinalbackend.entity.Paciente;
import com.clinicasorridente.pifinalbackend.repository.IEnderecoRepository;
import com.clinicasorridente.pifinalbackend.repository.IPacienteRepository;
import com.clinicasorridente.pifinalbackend.service.exception.DadoExistenteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PacienteService implements IGenericService<Paciente, Long> {

    @Autowired
    private IPacienteRepository pacienteRepository;

    @Autowired
    private IEnderecoRepository enderecoRepository;

    @Override
    public Paciente salvar(Paciente paciente) {
            return pacienteRepository.save(paciente);
    }

    @Override
    public Optional<Paciente> buscarPorId(Long id) {
        return pacienteRepository.findById(id);
    }

    @Override
    public List<Paciente> buscarTodos() {
        return pacienteRepository.findAll();
    }

    @Override
    public void excluirPorId(Long id) {
        try {
            pacienteRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("Não é possível excluir um paciente que está vinculado a uma " +
                    "consulta.");
        }
    }

}

