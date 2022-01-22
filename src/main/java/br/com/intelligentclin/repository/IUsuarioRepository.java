package br.com.intelligentclin.repository;

import br.com.intelligentclin.entity.Usuario;
import br.com.intelligentclin.entity.enums.Cargo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IUsuarioRepository extends IPessoaBaseRepository<Usuario>, JpaRepository<Usuario, Long> {


    List<Usuario> findByCargo(Cargo cargo);

    Optional<Usuario> findByLogin(String login);

    Optional<Usuario> findByEmail(String email);

}
