package br.com.intelligentclin.repository;

import br.com.intelligentclin.entity.Dentista;
import br.com.intelligentclin.entity.enums.Especialidade;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IDentistaRepository extends IPessoaBaseRepository<Dentista>, JpaRepository<Dentista, Long> {

    Optional<Dentista> findByMatriculaIgnoreCaseContains(String matricula);

    Page<Dentista> findByEspecialidadesContains(Pageable pageable, Especialidade especialidade);

}
