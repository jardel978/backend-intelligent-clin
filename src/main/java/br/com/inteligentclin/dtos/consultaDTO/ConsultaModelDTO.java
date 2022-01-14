package br.com.inteligentclin.dtos.consultaDTO;

import br.com.inteligentclin.entity.Dentista;
import br.com.inteligentclin.entity.Paciente;
import br.com.inteligentclin.entity.Usuario;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Builder
@Getter
@Setter
public class ConsultaModelDTO {

    private Long id;
    private Paciente paciente;
    private Dentista dentista;
    private Usuario usuario;
    private Date dataConsulta;
    private String horaConsulta;

}
