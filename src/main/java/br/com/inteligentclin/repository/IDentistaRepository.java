package br.com.inteligentclin.repository;

import br.com.inteligentclin.entity.Dentista;
import br.com.inteligentclin.entity.enums.Especialidade;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IDentistaRepository extends IPessoaBaseRepository<Dentista>, JpaRepository<Dentista, Long> {

    Optional<Dentista> findByMatriculaContains(String matricula);

    Page<Dentista> findByEspecialidadesContains(Pageable pageable, Especialidade especialidade);

}
