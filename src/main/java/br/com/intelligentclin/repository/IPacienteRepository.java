package br.com.intelligentclin.repository;

import br.com.intelligentclin.entity.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPacienteRepository extends JpaRepository<Paciente, Long> {


}
