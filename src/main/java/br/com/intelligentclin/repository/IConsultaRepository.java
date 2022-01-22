package br.com.intelligentclin.repository;

import br.com.intelligentclin.entity.Consulta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalTime;

public interface IConsultaRepository extends JpaRepository<Consulta, Long> {

    Consulta findByDataConsultaAndHoraConsulta(LocalDate dataConsulta, LocalTime horaConsulta);

}
