package br.com.inteligentclin.dtos.consultaDTO;

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
public class ConsultaMixClasseModelDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate dataConsulta;

    @JsonFormat(pattern = "HH:mm")
    private LocalTime horaConsulta;

    private StatusConsulta status;

}
