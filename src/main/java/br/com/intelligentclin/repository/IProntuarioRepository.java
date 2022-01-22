package br.com.intelligentclin.repository;

import br.com.intelligentclin.entity.Prontuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IProntuarioRepository extends JpaRepository<Prontuario, Long> {

}
