package br.com.intelligentclin.dtos.consultaDTO;

import br.com.intelligentclin.dtos.PessoaMixConsultaSummaryDTO;
import br.com.intelligentclin.dtos.usuarioDTO.UsuarioMixConsultaSummaryDTO;
import br.com.intelligentclin.entity.enums.StatusConsulta;
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
