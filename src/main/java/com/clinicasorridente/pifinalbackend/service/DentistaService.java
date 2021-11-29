package com.clinicasorridente.pifinalbackend.service;

import com.clinicasorridente.pifinalbackend.entity.Dentista;
import com.clinicasorridente.pifinalbackend.repository.IDentistaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.Optional;

@Service
public class DentistaService {

    @Autowired
    private IDentistaRepository dentistaRepository;

    public Dentista salvar(Dentista dentista) {
        return dentistaRepository.save(dentista);
    }

    public Optional<Dentista> buscarPorId(Long id) {
        return dentistaRepository.findById(id);
    }

    public List<Dentista> buscarTodos() {
        return dentistaRepository.findAll();
    }

    public void excluirPorId(Long id) {
        try {
            dentistaRepository.deleteById(id);
        }catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("Não é possível excluir um dentista que está vinculado a uma " +
                    "consulta.");
        }
    }
}
