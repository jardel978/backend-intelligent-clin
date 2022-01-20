package br.com.inteligentclin.dtos.consultaDTO;

import br.com.inteligentclin.dtos.PessoaMixConsultaSummaryDTO;
import br.com.inteligentclin.dtos.usuarioDTO.UsuarioMixConsultaSummaryDTO;
import br.com.inteligentclin.entity.enums.StatusConsulta;
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
    private PessoaMixConsultaSummaryDTO paciente;
    private PessoaMixConsultaSummaryDTO dentista;
    private UsuarioMixConsultaSummaryDTO usuario;

    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate dataConsulta;

    @JsonFormat(pattern = "HH:mm")
    private LocalTime horaConsulta;

    private StatusConsulta status;

}
