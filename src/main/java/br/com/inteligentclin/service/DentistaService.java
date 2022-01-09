package com.clinicasorridente.pifinalbackend.service;

import com.clinicasorridente.pifinalbackend.entity.Dentista;
import com.clinicasorridente.pifinalbackend.repository.IDentistaRepository;
import com.clinicasorridente.pifinalbackend.service.exception.DadoExistenteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

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

    public void atualizar(Long id, Dentista dentista) {
        Dentista dentistaDaBase =
                buscarPorId(id).orElseThrow(() ->
                        new DadoExistenteException("Dentista não encontrado.")
                );
        dentistaDaBase.setNome(dentista.getNome());
        dentistaDaBase.setSobrenome(dentista.getSobrenome());
        dentistaDaBase.setMatricula(dentista.getMatricula());
        dentistaDaBase.setConsultas(dentista.getConsultas());
        salvar(dentistaDaBase);
    }
}
