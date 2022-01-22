//package br.com.inteligentclin.service;
//
//import br.com.inteligentclin.data.UsuarioDetailsData;
//import br.com.inteligentclin.entity.Usuario;
//import br.com.inteligentclin.repository.IUsuarioRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import java.util.Optional;
//
//@Service
//public class UsuarioDetailsService implements UserDetailsService {
//
//    @Autowired
//    private IUsuarioRepository usuarioRepository;
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        Optional<Usuario> usuario = usuarioRepository.findByLogin(username);
//        if (usuario.isEmpty())
//            throw new UsernameNotFoundException("Usuário com login/emial: " + username + " não foi encontrado.");
//        return new UsuarioDetailsData(usuario);
//    }
//}
