package br.com.inteligentclin.repository;

import br.com.inteligentclin.entity.Prontuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IProntuarioRepository extends JpaRepository<Prontuario, Long> {

}
