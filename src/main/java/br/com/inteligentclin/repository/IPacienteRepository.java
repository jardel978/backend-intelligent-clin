package br.com.inteligentclin.repository;

import br.com.inteligentclin.entity.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPacienteRepository extends JpaRepository<Paciente, Long> {

//    Paciente findByCpf(String cpf);

}
