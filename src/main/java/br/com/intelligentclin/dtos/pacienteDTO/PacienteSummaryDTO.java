package br.com.intelligentclin.dtos.pacienteDTO;

import br.com.intelligentclin.dtos.PessoaSummaryDTO;
import br.com.intelligentclin.entity.Idade;
import br.com.intelligentclin.entity.enums.Sexo;
import lombok.*;

import java.io.Serializable;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PacienteSummaryDTO extends PessoaSummaryDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Idade idade;

    private Boolean temConsultas;

    private Sexo sexo;

}
