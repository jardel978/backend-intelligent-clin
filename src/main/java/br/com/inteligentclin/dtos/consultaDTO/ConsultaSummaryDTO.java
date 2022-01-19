package br.com.inteligentclin.dtos.consultaDTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ConsultaSummaryDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private Long idPaciente;
    private Long idDentista;
    private Long idUsuario;

    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate dataConsulta;

    @JsonFormat(pattern = "HH:mm")
    private LocalTime horaConsulta;

}
