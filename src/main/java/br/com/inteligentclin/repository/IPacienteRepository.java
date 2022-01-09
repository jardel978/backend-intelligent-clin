package com.clinicasorridente.pifinalbackend.repository;

import com.clinicasorridente.pifinalbackend.entity.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IPacienteRepository extends JpaRepository<Paciente, Long> {

    Paciente findByCpf(String cpf);

}
