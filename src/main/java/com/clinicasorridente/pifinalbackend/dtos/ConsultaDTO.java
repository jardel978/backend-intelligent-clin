package com.clinicasorridente.pifinalbackend.dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Builder
@Getter
@Setter
public class ConsultaDTO {

    private Long id;
    private Long idPaciente;
    private Long idDentista;
    private Long idUsuario;
    private Date dataConsulta;
    private String horaConsulta;

}
