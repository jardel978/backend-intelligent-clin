package br.com.inteligentclin.repository;

import br.com.inteligentclin.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface IUsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByLogin(String login);
//    @Query("from Usuario where login = :login and senha = :senha")
//    public Usuario findByLoginAndSenha(@Param("login") String login,
//                                       @Param("senha") String senha);

}
