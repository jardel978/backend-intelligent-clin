package br.com.inteligentclin.repository;

import br.com.inteligentclin.entity.Usuario;
import br.com.inteligentclin.entity.enums.Cargo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IUsuarioRepository extends IPessoaBaseRepository<Usuario>, JpaRepository<Usuario, Long> {

    Optional<Usuario> findByLogin(String login);

    List<Usuario> findByCargo(Cargo cargo);
//    @Query("from Usuario where login = :login and senha = :senha")
//    public Usuario findByLoginAndSenha(@Param("login") String login,
//                                       @Param("senha") String senha);

}
