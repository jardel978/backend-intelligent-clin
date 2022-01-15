package br.com.inteligentclin.dtos.consultaDTO;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ConsultaSummaryDTO implements Serializable  {

    private static final long serialVersionUID = 1L;

    private Long id;
    private Long idPaciente;
    private Long idDentista;
    private Long idUsuario;
    private LocalDate dataConsulta;
    private LocalTime horaConsulta;

}
