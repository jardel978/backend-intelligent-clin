package br.com.inteligentclin.dtos.consultaDTO;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Builder
@Getter
@Setter
public class ConsultaModelDTO {

    private Long id;
    private Long idPaciente;
    private Long idDentista;
    private Long idUsuario;
    private Date dataConsulta;
    private String horaConsulta;

}
