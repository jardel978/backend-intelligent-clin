package com.clinicasorridente.pifinalbackend.service;

import com.clinicasorridente.pifinalbackend.entity.Usuario;
import com.clinicasorridente.pifinalbackend.repository.IUsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService implements IGenericService<Usuario, Long> {

    @Autowired
    private IUsuarioRepository usuarioRepository;

    @Override
    public Usuario salvar(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    @Override
    public Optional<Usuario> buscarPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    @Override
    public List<Usuario> buscarTodos() {
        return usuarioRepository.findAll();
    }

    @Override
    public void excluirPorId(Long id) {
        try {
            usuarioRepository.deleteById(id);
        }catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("Não é possível excluir um usuário que está vinculado a uma " +
                    "consulta.");
        }
    }

}
