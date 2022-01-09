package br.com.inteligentclin.repository;

import br.com.inteligentclin.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUsuarioRepository extends JpaRepository<Usuario, Long> {



}
