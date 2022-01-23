package br.com.intelligentclin.service;

import br.com.intelligentclin.entity.Usuario;
import br.com.intelligentclin.repository.IUsuarioRepository;
import br.com.intelligentclin.security.data.UsuarioDetailsData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioDetailsService implements UserDetailsService {

    @Autowired
    private IUsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Usuario> usuario = usuarioRepository.findByEmail(username);
        if (usuario.isEmpty())
            throw new UsernameNotFoundException("Usuário com email: " + username + " não foi encontrado.");
        return new UsuarioDetailsData(usuario);
    }
}
