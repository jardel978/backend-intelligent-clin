package br.com.inteligentclin.dtos.pacienteDTO;

import br.com.inteligentclin.dtos.PessoaSummaryDTO;
import br.com.inteligentclin.entity.Idade;
import br.com.inteligentclin.entity.enums.Sexo;
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
